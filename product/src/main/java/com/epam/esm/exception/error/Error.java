package com.epam.esm.exception.error;

public enum Error {

      ERROR01("locale.message.error.EntityNotFound"),
      ERROR02("locale.message.error.MalformedJSONRequest"),
      ERROR03("locale.message.error.CheckTheUrlParams"),
      ERROR04("locale.message.error.ArgumentNotValid"),
      ERROR05("locale.message.error.HaveNotRights"),
      ERROR06("locale.message.error.TokenNotValid"),
      ERROR07("locale.message.error.EntityAlreadyExist"),
      ERROR08("locale.message.error.SomethingWentWrong"),
      ERROR09("locale.message.error.PasswordNotMatch");
  private final String description;

    Error(String description) {
        this.description = description;
    }

    public String getDescription() {
       return description;
    }
}
