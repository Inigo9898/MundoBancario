package es.eoi.mundobancario.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

//El mapeo DTO - Entity de las Listas requiere de esta Subclase, para que el proceso sera rapido y sencillo.
//Lo he sacado como una clase Util aparte porque se utiliza mas de una vez.
//Lo he sacado de StackOverFlow y Baeldung. 
//https://stackoverflow.com/questions/47929674/modelmapper-mapping-list-of-entites-to-list-of-dto-objects

//Mas o menos, entiendo qué se hace en cada parte. 
public class ObjectMapperUtils {
	// private static ModelMapper modelMapper = new ModelMapper();

	@Autowired
	static ModelMapper modelMapper;

	static {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	private ObjectMapperUtils() {
	}

	public static <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}

}
