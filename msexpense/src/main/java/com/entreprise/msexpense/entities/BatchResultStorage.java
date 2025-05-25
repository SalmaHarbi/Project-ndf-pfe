package com.entreprise.msexpense.entities;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BatchResultStorage {
    private List<Depense> depenses = new ArrayList<>();

    // changer la signature
    public void addAll(Collection<? extends Depense> items) {
        depenses.addAll(items);
    }

    // méthode pour récupérer les résultats
    public List<Depense> getDepenses() {
        return depenses;
    }
}

