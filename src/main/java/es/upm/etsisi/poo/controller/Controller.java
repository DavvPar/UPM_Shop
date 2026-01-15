package es.upm.etsisi.poo.controller;

import es.upm.etsisi.poo.MapDB.MapDBManager;

public class Controller {
    MapDBManager mapDBManager;
    public Controller(MapDBManager mapDBManager){
        this.mapDBManager = mapDBManager;
    }
}
