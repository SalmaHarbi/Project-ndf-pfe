package com.entreprise.msexpense.batchConfig;

import com.entreprise.msexpense.entities.BatchResultStorage;
import com.entreprise.msexpense.entities.Depense;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;



@Component
public class DatabaseItemWriter implements ItemWriter<Depense> {

    private final BatchResultStorage storage;

    public DatabaseItemWriter(BatchResultStorage storage) {
        this.storage = storage;
    }


    @Override
    public void write(Chunk<? extends Depense> items) throws Exception {
        storage.addAll(items.getItems());
    }
}
