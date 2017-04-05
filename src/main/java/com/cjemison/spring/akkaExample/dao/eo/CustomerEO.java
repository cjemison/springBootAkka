package com.cjemison.spring.akkaExample.dao.eo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by cjemison on 4/4/17.
 */
@Entity
public class CustomerEO implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;

  protected CustomerEO() {
  }

  public CustomerEO(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof CustomerEO)) return false;

    final CustomerEO customerEO = (CustomerEO) o;

    if (getId() != null ? !getId().equals(customerEO.getId()) : customerEO.getId() != null)
      return false;
    if (getFirstName() != null ? !getFirstName().equals(customerEO.getFirstName()) : customerEO
          .getFirstName() != null)
      return false;
    return getLastName() != null ? getLastName().equals(customerEO.getLastName()) : customerEO
          .getLastName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
    result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format(
          "CustomerEO[id=%d, firstName='%s', lastName='%s']",
          id, firstName, lastName);
  }
}
