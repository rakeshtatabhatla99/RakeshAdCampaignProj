package com.ad.campaign;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/AdCampaignService")
public class AdCampaignServices {
	public static AdCampaignDAO dao = new AdCampaignDAO();
	
	 @GET
	   @Path("/allad")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Ad> getAd(){
		 System.out.println("getAd called....");
	      return dao.getAllAds();
	   }	
	 
	 @GET
	    @Path("/{param}")
	    public Ad getMsg(@PathParam("param") String partnerId) throws Exception {
	        System.out.println("locating Ad for partnerId "+partnerId);
	        
	        return dao.getAd(partnerId);
	    }
	 
	 @POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_JSON})
	    @Path("/createad")
	    public String createAd(Ad ad) throws Exception{
	        System.out.println("createAd method called");
	        System.out.println("Ad Contents = "+ad.getAd_content());
	        System.out.println("partner id  = "+ad.getPartner_id());
	        System.out.println("Duration = "+ad.getDuration());
	        ad.setCreatedTime(new Date());
	        System.out.println("going to create ad");
	        dao.createAd(ad);
	        System.out.println("Ad Created!!");
	        return "ok";
	    }
}
