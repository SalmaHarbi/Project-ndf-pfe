package com.entreprise.msexpense.entities;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BatchResultStorage {
    private List<Depense> depenses = new ArrayList<>();

    public void addAll(Collection<? extends Depense> items) {
        depenses.addAll(items);
    }

    public List<Depense> getDepenses() {
        return depenses;
    }
}

