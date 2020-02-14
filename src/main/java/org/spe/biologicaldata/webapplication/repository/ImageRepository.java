package org.spe.biologicaldata.webapplication.repository;

import org.spe.biologicaldata.webapplication.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
