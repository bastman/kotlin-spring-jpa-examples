package com.example;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class FooEntity {

    @Id
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
