/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.errores;

/**
 *
 * @author juamp
 */
public class ErrorServicio extends Exception{
    public ErrorServicio(String msj){
        super(msj);
    }
}
