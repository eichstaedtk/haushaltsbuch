package de.eichstaedt.haushaltsbuch.application.model;

import java.util.Map;

/**
 * Created by konrad.eichstaedt@gmx.de on 2019-08-07.
 */
public class SubnavigationModel {

  public SubnavigationModel(String title, Map<String,String> navItems) {
    this.title = title;
    this.navItems = navItems;
  }

  private String title;

  private Map<String,String> navItems;

  private String activeItem;

  public String getTitle() {
    return title;
  }

  public Map<String,String> getNavItems() {
    return navItems;
  }

  public String getActiveItem() {
    return activeItem;
  }

  public void setActiveItem(String activeItem) {
    this.activeItem = activeItem;
  }
}
