package practica13.Repositories;

import practica13.Models.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {

    Encuesta findEncuestaById(Long id);

    Encuesta findEncuestaByCumplieronExpectativas(Long id);  //Deberia usarse para obtener el promedia de todas las encuestas.

}
