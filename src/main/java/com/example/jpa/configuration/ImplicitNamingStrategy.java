package com.example.jpa.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

public class ImplicitNamingStrategy extends SpringImplicitNamingStrategy {

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        Identifier userProvidedIdentifier = source.getUserProvidedIdentifier();
        if (userProvidedIdentifier != null) {
            return userProvidedIdentifier;
        }

        String foreignKeyName = (source.getTableName() + "_" + source.getReferencedTableName() + "_fk").toUpperCase();
        return toIdentifier(foreignKeyName, source.getBuildingContext());
    }

}
