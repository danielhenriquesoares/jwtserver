/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.utils;

import org.json.JSONObject;

/**
 *
 * @author daniel
 */
public class Response {
    private String data;
    private JSONObject error;
    private int type;
    
    /*public Response() {
        
    }*/
    
    public Response setData(String data) {
        this.data = data;
        return this;
    }
    
    public Response setType(int type) {
        this.type = type;
        return this;
    }
    
    public String build() {
        JSONObject resp = new JSONObject()
                .put("data", this.data)
                .put("type", this.type);
        
        return resp.toString();
    }
}
