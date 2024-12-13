package org.cst8277.Muhammad_Tariq.Dto;


public class SubscribeDto {
	private String subscriber_Id;
	private String producer_Id;
	
	public String getSubscriber_Id() {
		return subscriber_Id;
		
	}
	
	public String getProducer_Id() {
		return producer_Id;
		
	}
	
	public void setProducerId(String producer_Id) {
		this.producer_Id = producer_Id;
	}
	
	public void setSubscriberId(String subscriber_Id) {
		this.subscriber_Id = subscriber_Id;
	}

}