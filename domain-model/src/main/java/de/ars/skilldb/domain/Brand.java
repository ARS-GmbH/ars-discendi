package de.ars.skilldb.domain;

import java.util.Objects;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Brand {

    @GraphId
    private Long id;
    @Indexed(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(id, name);
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Brand)) { return false; }
        
        Brand other = (Brand) obj;
        if (!Objects.equals(this.id, other.id)) { return false; }
        if (!Objects.equals(this.name, other.name)) { return false; }
        
        return true;
    }
}
