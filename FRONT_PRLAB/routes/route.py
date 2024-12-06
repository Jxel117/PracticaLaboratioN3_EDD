from flask import Blueprint, abort, request, render_template, redirect, url_for
import json
import requests
from flask import flash
from flask import Blueprint, jsonify
# from transacciones import Transaccion, guardar_transaccion
from urllib.parse import quote


router = Blueprint ('router', __name__)

@router.route('/')
def home():

    return render_template('fragmento/inicial/login.html')

@router.route('/admin')
def admin():
    return render_template('fragmento/inicial/admin.html')

@router.route('/admin/familia/register')
def view_register_familia():
    r_familia = requests.get("http://localhost:8086/api/familia/list")
    data_familia = r_familia.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()

    return render_template('fragmento/familia/registro.html', lista_familia=data_familia["data"],lista_generador=data_generador["data"])

@router.route('/admin/familia/list')
def list_person(msg=''):
    r_familia = requests.get("http://localhost:8086/api/familia/list")
    data_familia = r_familia.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()
    print(data_familia)
    
    return render_template('fragmento/familia/lista.html', lista_familia=data_familia["data"],lista_generador=data_generador["data"])

@router.route('/admin/familia/edit/<id>', methods=['GET'])
def view_edit_person(id):
    # Obtener la lista de tipos de familia
    r = requests.get("http://localhost:8086/api/familia/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    # Obtener los datos de la familia por ID
    r1 = requests.get(f"http://localhost:8086/api/familia/get/{id}")

    if r1.status_code == 200:
        data_familia = r1.json()
        # Verificamos que la respuesta contenga datos válidos
        if "data" in data_familia and data_familia["data"]:
            familia = data_familia["data"]

            # Obtener los datos del generador asociado a la familia
            if familia["tieneGenerador"]:
                r_generador = requests.get(f"http://localhost:8086/api/generador/get/{familia['id']}")
                if r_generador.status_code == 200:
                    data_generador = r_generador.json()
                    generador = data_generador["data"] if "data" in data_generador else None
                else:
                    generador = None  # O manejar el error de alguna manera

            else:
                generador = None

            return render_template('fragmento/familia/editar.html', lista=lista_tipos["data"], familia=familia, generador=generador)
        else:
            flash("No se encontraron datos para la familia.", category='error')
            return redirect("/admin/familia/list")
    else:
        flash("Error al obtener la familia", category='error')
        return redirect("/admin/familia/list")
    


@router.route('/admin/familia/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Crear el diccionario para la familia
    data_familia = {
        "canton": form["can"],
        "apellidoPaterno": form["ape"],
        "apellidoMaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == 'true'  # Asumiendo que el valor es un string 'true' o 'false'
    }

    # Inicializa el diccionario para el generador si se necesita
    if form["tieneg"] == 'true':  # Solo si tiene generador
        data_generador = {
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:
        # Inicializa el generador a valores predeterminados
        data_generador = {
            "costo": 0,  # Inicializa a 0
            "consumoXHora": 0,  # Inicializa a 0
            "energiaGenerada": 0,  # Inicializa a 0
            "uso": 'ninguno',  # Inicializa a None
        }

    # Hacer la petición para guardar la familia
    r_familia = requests.post("http://localhost:8086/api/familia/save", data=json.dumps(data_familia), headers=headers)

    transaccion = Transaccion("Guardar Familia", "Se ha guardado una familia nueva.")
    guardar_transaccion(transaccion)
    
    requests.post("http://localhost:8086/api/generador/save", data=json.dumps(data_generador), headers=headers)

    transaccion = Transaccion("Guardar Generador", "Se ha guardado un generador nuevo.")
    guardar_transaccion(transaccion)

    transaccion = Transaccion("Guardar Generador", {"id": id})
    guardar_transaccion(transaccion)

    if r_familia.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/familia/list')
    else:
        flash(r_familia.json().get("data", "Error al guardar la familia"), category='error')
        return redirect('/admin/familia/list')

        
@router.route('/admin/familia/update', methods=['POST'])
def update_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Asegúrate de que estos campos sean los correctos según tu formulario HTML
    data_familia = {
        "id": form["id"],
        "canton": form["can"],
        "apellidoPaterno": form["ape"],
        "apellidoMaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == 'true'
    }

    if form["tieneg"] == 'true':  # Solo si tiene generador
        data_generador = {
            "id": form["id"],  # Usar el mismo ID que la familia
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:
        # Inicializa el generador a valores predeterminados
        data_generador = {
            "id": form["id"],  # Usar el mismo ID que la familia
            "costo": 0,  # Inicializa a 0
            "consumoXHora": 0,  # Inicializa a 0
            "energiaGenerada": 0,  # Inicializa a 0
            "uso": 'ninguno',  # Inicializa a None
        }
    # Envía los datos de la familia
    print("Datos del generador:", data_generador)  # Verifica lo que estás enviando
    r_generador = requests.post("http://localhost:8086/api/generador/update", data=json.dumps(data_generador), headers=headers)

    transaccion = Transaccion("Actualización Generador", {"id": id})
    guardar_transaccion(transaccion)

    if r_generador.status_code != 200:
        flash("Error al actualizar el generador: " + r_generador.json().get("data", ""), category='error')
   
    r_familia = requests.post("http://localhost:8086/api/familia/update", data=json.dumps(data_familia), headers=headers)

    transaccion = Transaccion("Actualización Familia", {"id": id})
    guardar_transaccion(transaccion)
    
    if r_familia.status_code == 200:
        flash("Registro de familia guardado correctamente", category='info')
        return redirect('/admin/familia/list')
    else:
        flash(r_familia.json().get("data", "Error al actualizar la familia"), category='error')
        return redirect('/admin/familia/list')

@router.route('/admin/familia/delete/<int:id>', methods=['POST'])
def delete_familia(id):
    # Realizar la solicitud DELETE a la API de Java
    requests.delete(f"http://localhost:8086/api/familia/delete/{id}")

    transaccion = Transaccion("Eliminar Familia", {"id": id})
    guardar_transaccion(transaccion)

    requests.delete(f"http://localhost:8086/api/generador/delete/{id}")

    transaccion = Transaccion("Eliminar Generador", {"id": id})
    guardar_transaccion(transaccion)

        # Redirigir al usuario a la lista de familias
    return redirect('/admin/familia/list')


def load_data(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)

@router.route('/admin/familia_generador', methods=['GET'])
def get_familia_generador_data():
    # Cargar datos de familias y generadores desde JSON
    familias = load_data('/home/joel117/Escritorio/BACK_PRLAB/src/main/java/Data/Familia.json')
    generadores = load_data('/home/joel117/Escritorio/BACK_PRLAB/src/main/java/Data/Generador.json')

    # Crear lista para almacenar la relación de familias y generadores
    familias_generadores = []
    for familia in familias:
        generador = next((g for g in generadores if g['id'] == familia['id']), None)
        familias_generadores.append({
            "familia": familia,
            "generador": generador
        })

    # Obtener el conteo de familias con generador
    response = requests.get('http://localhost:8086/api/familia/contadorGeneradores')
    data = response.json()
    familias_con_generador = data['familiasConGenerador']

    # Calcular el total de familias
    total_familias = len(familias)

    # Renderizar plantilla con ambas listas
    return render_template(
        'fragmento/familia/familia_generador.html',
        familias_generadores=familias_generadores,
        familias_con_generador=familias_con_generador,
        total_familias=total_familias
    )

@router.route('/admin/familia/list/<atributo>/<tipo>/<metodo>')
def view_order_person(atributo, tipo, metodo):
    try:
        print(f"Atributo reconocido!: atributo={atributo}, tipo={tipo}, metodo={metodo}")

        if metodo == "qs":
            url = f"http://localhost:8086/api/familia/list/qs/{atributo}/{tipo}"
        elif metodo == "ms":
            url = f"http://localhost:8086/api/familia/list/ms/{atributo}/{tipo}"
        elif metodo == "ss":
            url = f"http://localhost:8086/api/familia/list/ss/{atributo}/{tipo}"
        else:
            flash("Método de ordenación no válido", category='error')
            return render_template('fragmento/familia/lista.html', list=[])

        r = requests.get(url)

        if r.status_code == 200:
            data = r.json()
            return render_template('fragmento/familia/lista.html', lista_familia=data["data"])
        else:
            flash("Error al ordenar los datos", category='error')
            return render_template('fragmento/familia/lista.html', lista_familia=[], message='Error al ordenar los datos')

    except requests.RequestException as e:
        flash(f"Error de conexión: {str(e)}", category='error')
        return redirect("/admin/familia/list")

BASE_URL = "http://localhost:8086/api/familia/list/bus/"

@router.route('/admin/familia/bus/<criterio>/<texto>')
def view_buscar_person(criterio, texto):
    criterios_api = {
        "apellidopaterno": "apellidoPa",
        "apellidomaterno": "apellidoMa",
        "canton": "canton"
    }

    if criterio not in criterios_api:
        return jsonify([]), 400

    criterio_api = criterios_api[criterio]
    texto_codificado = quote(texto)
    full_url = f"{BASE_URL}{criterio_api}/{texto_codificado}"
    print(f"[DEBUG] URL generada: {full_url}")

    try:
        response = requests.get(full_url)
        response.raise_for_status()
        data = response.json()
        lista_familia = data.get("data", [])
        return jsonify(lista_familia)
    except requests.RequestException as e:
        print(f"[ERROR] Error al conectar con la API: {e}")
        return jsonify([]), 500



