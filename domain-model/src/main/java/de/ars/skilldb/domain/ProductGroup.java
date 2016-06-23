package de.ars.skilldb.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

/**
 * Repräsentiert eine Produktgruppe.
 */
@NodeEntity
public class ProductGroup {

    @GraphId
    private Long id;

    @NotBlank(message = "Der Name der Produktgruppe darf nicht leer sein.")
    @Indexed(unique = true)
    private String name;

    private String description;
    private boolean open;

    @RelatedTo(type = "IS_PART_OF", direction = Direction.OUTGOING)
    private Set<Brand> brands;

    /**
     * Erzeugt eine neue {@code ProductGroup}.
     */
    public ProductGroup() {
        brands = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(final Set<Brand> brand) {
        brands = brand;
    }

    /**
     * Fügt eine neue {@code Brand} zu dieser {@code ProductGroup} hinzu.
     *
     * @param brand
     *            die {@code Brand}, die hinzugefügt werden soll.
     */
    public void addBrand(final Brand brand) {
        brands.add(brand);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name, description, open, brands);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof ProductGroup)) { return false; }

        ProductGroup other = (ProductGroup) obj;
        if (!Objects.equals(id, other.id)) { return false; }
        if (!Objects.equals(name, other.name)) { return false; }
        if (!Objects.equals(description, other.description)) { return false; }
        if (!Objects.equals(open, other.open)) { return false; }
        if (!Objects.equals(brands, other.brands)) { return false; }

        return true;
    }
}
