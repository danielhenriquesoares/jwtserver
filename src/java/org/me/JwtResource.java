/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.me.utils.Jwt;
import org.me.utils.Response;



/**
 * REST Web Service
 *
 * @author daniel
 */
@Path("jwt")
public class JwtResource {

    /*@Context
    private UriInfo context;*/
    private Jws<Claims> jws;
    private Claims claims;
    //private Claims jws;

    /**
     * Creates a new instance of GenericResource
     */
    public JwtResource() {
    }

    /**
     * Retrieves representation of an instance of org.me.JwtResource
     * @return an instance of java.lang.String
     * @throws java.io.UnsupportedEncodingException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String jwt() throws UnsupportedEncodingException {
        //TODO return proper representation object
        //return "Hello World!";
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        //long miliseconds = calendar.getTimeInMillis();
        //long t = System.currentTimeMillis();
        /*Date dt = new Date(TimeUnit.MILLISECONDS.toSeconds(miliseconds));*/
        Date d = calendar.getTime();
        
        String jwt;
        jwt = Jwts.builder()
                .setSubject("/test")
                //.setExpiration(new GregorianCalendar(2019, 11, 31).getTime())
                //.setExpiration(new Date(calendar.getTimeInMillis()))
                .setExpiration(d)
                .claim("name", "Daniel Soares")
                .claim("scope", "another test")
                .signWith(
                        SignatureAlgorithm.HS256, 
                        "secret".getBytes("UTF-8")
                )
                .compact();
        
        JSONObject json = new JSONObject()
                .put("result", jwt);
        
        return json.toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String jwt(Jwt jwt) {
        
        try {
 
            if (Jwts.parser().isSigned(jwt.getJwt())) {
                this.jws = Jwts.parser()
                    .setSigningKey("s3cr3t".getBytes("UTF-8"))
                    .parseClaimsJws(jwt.getJwt());
             
                this.claims = this.jws.getBody();
                
                Calendar calendar = Calendar.getInstance();
                long t = (int) this.claims.get("exp");
                calendar.setTimeInMillis(t * 1000L);
                
                System.out.println("Sub: " + this.claims.get("sub"));
                System.out.println("Name: " + this.claims.get("name"));
                System.out.println("Scope: " + this.claims.get("scope"));
                System.out.println("Expiration: " + calendar.getTime());
            }
            
            
        } catch(JwtException ex) {
            System.out.println("exceptiom: " + ex.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JwtResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return new Response()
                .setData("success")
                .setType(1)
                .build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }*/
}
