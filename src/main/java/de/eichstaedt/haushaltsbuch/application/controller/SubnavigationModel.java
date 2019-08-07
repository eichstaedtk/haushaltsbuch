package de.eichstaedt.haushaltsbuch.application.controller;

import java.util.List;

/**
 * Created by konrad.eichstaedt@gmx.de on 2019-08-07.
 */
public class SubnavigationModel {

  public SubnavigationModel(String title, List<String> navItems) {
    this.title = title;
    this.navItems = navItems;
  }

  private String title;

  private List<String> navItems;

  private String activeItem;

  public String getTitle() {
    return title;
  }

  public List<String> getNavItems() {
    return navItems;
  }

  public String getActiveItem() {
    return activeItem;
  }

  public void setActiveItem(String activeItem) {
    this.activeItem = activeItem;
  }
}
