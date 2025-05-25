package com.entreprise.msexpense.controllers;
import com.entreprise.msexpense.entities.BatchResultStorage;
import com.entreprise.msexpense.entities.Depense;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    private final BatchResultStorage storage;

    public JobController(BatchResultStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/depenses/filtrees")
    public List<Depense> getFilteredDepenses() {
        return storage.getDepenses();
    }
}

