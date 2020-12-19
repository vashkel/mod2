package com.epam.esm.exception.error;

public enum Error {

      ERROR01("locale.message.EntityNotFound"),
      ERROR02("locale.message.MalformedJSONRequest"),
      ERROR03("locale.message.CheckTheUrlParams"),
      ERROR04("locale.message.ArgumentNotValid"),
      ERROR05("locale.message.CheckLoginData");

  private final String description;

    Error(String description) {
        this.description = description;
    }

    public String getDescription() {
       return description;
    }
}
