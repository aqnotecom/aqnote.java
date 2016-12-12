/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.structural.apdater;

public class RunAdapterPattern {
    public static void main(String [] arguments){
        System.out.println("Example for the Adapter pattern");
        System.out.println();
        System.out.println("This example will demonstrate the Adapter by using the");
        System.out.println(" class ContactAdapter to translate from classes written");
        System.out.println(" in a foreign language (Chovnatlh and ChovnatlhImpl),");
        System.out.println(" enabling their code to satisfy the Contact interface.");
        System.out.println();
        
        System.out.println("Creating a new ContactAdapter. This will, by extension,");
        System.out.println(" create an instance of ChovnatlhImpl which will provide");
        System.out.println(" the underlying Contact implementation.");
        Contact contact = new ContactAdapter();
        System.out.println();
        
        System.out.println("ContactAdapter created. Setting contact data.");
        contact.setFirstName("Thomas");
        contact.setLastName("Williamson");
        contact.setTitle("Science Officer");
        contact.setOrganization("W3C");
        System.out.println();
        
        System.out.println("ContactAdapter data has been set. Printing out Contact data.");
        System.out.println();
        System.out.println(contact.toString());
    }
}

interface Contact{
    public String getFirstName();
    public String getLastName();
    public String getTitle();
    public String getOrganization();
    
    public void setContact(Chovnatlh newContact);
    public void setFirstName(String newFirstName);
    public void setLastName(String newLastName);
    public void setTitle(String newTitle);
    public void setOrganization(String newOrganization);
}

class ContactAdapter implements Contact{
    private Chovnatlh contact;
    
    public ContactAdapter(){
        contact = new ChovnatlhImpl();
    }
    public ContactAdapter(Chovnatlh newContact){
        contact = newContact;
    }
    
    public String getFirstName(){
        return contact.tlhapWa$DIchPong();
    }
    public String getLastName(){
        return contact.tlhapQavPong();
    }
    public String getTitle(){
        return contact.tlhapPatlh();
    }
    public String getOrganization(){
        return contact.tlhapGhom();
    }
    
    public void setContact(Chovnatlh newContact){
        contact = newContact;
    }
    public void setFirstName(String newFirstName){
        contact.cherWa$DIchPong(newFirstName);
    }
    public void setLastName(String newLastName){
        contact.cherQavPong(newLastName);
    }
    public void setTitle(String newTitle){
        contact.cherPatlh(newTitle);
    }
    public void setOrganization(String newOrganization){
        contact.cherGhom(newOrganization);
    }
    
    public String toString(){
        return contact.toString();
    }
}

interface Chovnatlh {
  public String tlhapWa$DIchPong();

  public String tlhapQavPong();

  public String tlhapPatlh();

  public String tlhapGhom();

  public void cherWa$DIchPong(String chu$wa$DIchPong);

  public void cherQavPong(String chu$QavPong);

  public void cherPatlh(String chu$patlh);

  public void cherGhom(String chu$ghom);
}

//pong = name
//wa'DIch = first
//Qav = last
//patlh = rank (title)
//ghom = group (organization)
//tlhap = take (get)
//cher = set up (set)
//chu' = new
//chovnatlh = specimen (contact)

class ChovnatlhImpl implements Chovnatlh {
  private String wa$DIchPong;

  private String QavPong;

  private String patlh;

  private String ghom;

  public ChovnatlhImpl() {
  }

  public ChovnatlhImpl(String chu$wa$DIchPong, String chu$QavPong,
      String chu$patlh, String chu$ghom) {
    wa$DIchPong = chu$wa$DIchPong;
    QavPong = chu$QavPong;
    patlh = chu$patlh;
    ghom = chu$ghom;
  }

  public String tlhapWa$DIchPong() {
    return wa$DIchPong;
  }

  public String tlhapQavPong() {
    return QavPong;
  }

  public String tlhapPatlh() {
    return patlh;
  }

  public String tlhapGhom() {
    return ghom;
  }

  public void cherWa$DIchPong(String chu$wa$DIchPong) {
    wa$DIchPong = chu$wa$DIchPong;
  }

  public void cherQavPong(String chu$QavPong) {
    QavPong = chu$QavPong;
  }

  public void cherPatlh(String chu$patlh) {
    patlh = chu$patlh;
  }

  public void cherGhom(String chu$ghom) {
    ghom = chu$ghom;
  }

  public String toString() {
    return wa$DIchPong + " " + QavPong + ": " + patlh + ", " + ghom;
  }
}
