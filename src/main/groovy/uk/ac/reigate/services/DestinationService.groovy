package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.ilr.Destination
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilr.DestinationRepository

@Service
class DestinationService implements ICoreDataService<Destination, Integer>{
    
    @Autowired
    DestinationRepository destinationRepository
    
    /**
     * Default NoArgs constructor
     */
    DestinationService() {}
    
    /**
     * Autowired Constructor
     *
     * @param destinationRepository
     */
    DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository
    }
    
    /**
     * Find an individual Destination using the Destination ID fields
     *
     * @param id The ID fields to search for
     * @return The Destination object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Destination findById(Integer id) {
        return destinationRepository.findOne(id)
    }
    
    /**
     * Find all Destinations
     *
     * @return a List set with the list of Destination
     */
    @Override
    @Transactional(readOnly = true)
    List<Destination> findAll() {
        return destinationRepository.findAll()
    }
    
    // Yet to be implemented  Feature #4104
    @Override
    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Block should not be deleted.
     */
    @Override
    public void delete(Destination obj) {
        throw new InvalidOperationException("Destination should not be deleted")
        
    }
    
}
