package com.example.rest;

import controller.Dao.servicies.FamiliaServicies;
import java.util.HashMap;
import controller.tda.list.LinkedList;
import models.Familia;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.sound.sampled.Line;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("familia")
public class FamiliaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    // @Path("/list/order/{att}/{type}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getFamiliaLastName(@PathParam("att") String att,
    // @PathParam("type") Integer type) {
    // HashMap map = new HashMap<>();
    // FamiliaServicies ps = new FamiliaServicies();
    // map.put("msg", "Ok");
    // try {
    // LinkedList lista = ps.order_object(type, att);
    // map.put("data", lista.toArray());
    // if (lista.isEmpty()) {
    // map.put("data", new Object[] {});
    // }
    // } catch (Exception e) {
    // // TODO handle exception
    // }
    // return Response.ok(map).build();
    // }

    @Path("/list/qs/{att}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsLastName(@PathParam("att") String att, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        try {
            //revisar el order
            LinkedList lista = ps.orderQuick(type, att);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
            // TODO handle exception
        }

        return Response.ok(map).build();
    }

    @Path("/list/ms/{att}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamiliaLastName(@PathParam("att") String att, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        try {
            //revisar el order
            LinkedList lista = ps.orderMerge(type, att);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
            // TODO handle exception
        }

        return Response.ok(map).build();
    }

    @Path("/list/ss/{att}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamilyLt(@PathParam("att") String att, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        try {
            //revisar el order
            LinkedList lista = ps.orderShell(type, att);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
            // TODO handle exception
        }

        return Response.ok(map).build();
    }

    // @Path("/list/search/{texto}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getFamiliaLastNameFa(@PathParam("texto") String texto) {
    //     HashMap map = new HashMap<>();
    //     FamiliaServicies ps = new FamiliaServicies();
    //     map.put("msg", "Ok");
    //     LinkedList lista = ps.buscar_apellidoPa(texto);
    //     map.put("data", lista.toArray());
    //     if (lista.isEmpty()) {
    //         map.put("data", new Object[] {});

    //     }
    //     return Response.ok(map).build();
    // }

    // @Path("/list/search/canton/{texto}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getFamiliaCanton(@PathParam("texto") String texto) {
    //     HashMap map = new HashMap<>();
    //     FamiliaServicies ps = new FamiliaServicies();
    //     map.put("msg", "Ok");
    //     LinkedList lista = ps.buscarxcanton(texto);
    //     map.put("data", lista.toArray());
    //     if (lista.isEmpty()) {
    //         map.put("data", new Object[] {});

    //     }
    //     return Response.ok(map).build();
    // }

    @Path("/list/bus/apellidoPa/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.buscarxapellidoPa(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/apellidoMa/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNaMa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.buscarxapellidoMa(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/canton/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamCanton(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.buscarxcanton(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/lineal/apellidoPa/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNaLin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busApellidoPaLin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/bin/apellidoPa/{texto}")
    @GET   
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNaBin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busApellidoPaBin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }


    @Path("/list/bus/lineal/apellidoMa/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNaMaLin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busApellidoMaLin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/bin/apellidoMa/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamLasNaMaBin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busApellidoMaBin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/lineal/canton/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamCantonLin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busCantonLin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/list/bus/bin/canton/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamCantonBin(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busCantonBin(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        try {
            ps.setFamilia(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getFamilia());
        if (ps.getFamilia().getId() == null) {
            map.put("data", "No existe la familia con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        // todo
        // Validation

        HashMap res = new HashMap<>();

        try {

            FamiliaServicies ps = new FamiliaServicies();
            ps.getFamilia().setCanton(map.get("canton").toString());
            ps.getFamilia().setApellidoPaterno(map.get("apellidoPaterno").toString());
            ps.getFamilia().setApellidoMaterno(map.get("apellidoMaterno").toString());
            ps.getFamilia().setIntegrantes(Integer.parseInt(map.get("integrantes").toString()));
            ps.getFamilia().setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));

            ps.save();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        // todo
        // Validation

        HashMap res = new HashMap<>();

        try {

            FamiliaServicies ps = new FamiliaServicies();
            ps.setFamilia(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getFamilia().setCanton(map.get("canton").toString());
            ps.getFamilia().setApellidoPaterno(map.get("apellidoPaterno").toString());
            ps.getFamilia().setApellidoMaterno(map.get("apellidoMaterno").toString());
            ps.getFamilia().setIntegrantes(Integer.parseInt(map.get("integrantes").toString()));
            ps.getFamilia().setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));

            ps.update();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/listtype")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response gettype() {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getFamilia());
        return Response.ok(map).build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFamilia(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            FamiliaServicies fs = new FamiliaServicies();

            // Intentar eliminar la familia
            boolean familiaDeleted = fs.delete(id - 1);

            if (familiaDeleted) {
                res.put("message", "Familia y Generador eliminados exitosamente");
                return Response.ok(res).build();
            } else {
                // Si no se eliminó, enviar un error 404
                res.put("message", "Familia no encontrada o no eliminada");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            // En caso de error, devolver una respuesta de error interno
            res.put("message", "Error al intentar eliminar la familia");
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
