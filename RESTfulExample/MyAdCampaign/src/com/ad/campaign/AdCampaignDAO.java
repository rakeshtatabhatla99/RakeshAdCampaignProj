package com.ad.campaign;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdCampaignDAO {
	private Map<String,Ad> adMap;
	public AdCampaignDAO(){
		System.out.println("AdCampaignDAO called");
		 adMap = new HashMap<String, Ad>();
	}
	
	
	public void createAd(Ad ad) throws Exception{
		if(adMap.containsKey(ad.getPartner_id())){
			Ad oldAd = adMap.get(ad.getPartner_id());
			
			Calendar newCal = Calendar.getInstance();
			newCal.setTime(ad.getCreatedTime());
			Calendar oldCal = Calendar.getInstance();
			oldCal.setTime(oldAd.getCreatedTime());
			oldCal.add(Calendar.SECOND, oldAd.getDuration());
			Date dt = new Date(oldCal.getTimeInMillis());
			System.out.println(dt);
			if(newCal.getTimeInMillis() <= oldCal.getTimeInMillis()){
				throw new Exception ("Ad is already created and live");
			}
			adMap.remove(ad.getPartner_id());
		}
		adMap.put(ad.getPartner_id(), ad);
	}
	
	public boolean validateAd(Ad oldAd, Date currDate){
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(currDate);
		Calendar oldCal = Calendar.getInstance();
		oldCal.setTime(oldAd.getCreatedTime());
		oldCal.add(Calendar.SECOND, oldAd.getDuration());
		Date dt = new Date(oldCal.getTimeInMillis());
		System.out.println(dt);
		return newCal.getTimeInMillis() <= oldCal.getTimeInMillis();
	}
	
	public Ad getAd(String partnerId) throws Exception{
		System.out.println("returning AD for "+partnerId);
		Ad ad = adMap.get(partnerId);
		if(ad == null){
			throw new Exception("There is no live AD found with partner_id : "+partnerId);
		}
		if(validateAd(ad, new Date())){
			return ad;
		}
		throw new Exception("OLD AD found but expired having partner_id : "+partnerId);
	}
	
	public List<Ad> getAllAds(){
		List<Ad> allAds = new ArrayList<Ad>();
		allAds.addAll(adMap.values());
		System.out.println("return all Ad size "+allAds.size());
		return allAds;
	}
	
	
}
