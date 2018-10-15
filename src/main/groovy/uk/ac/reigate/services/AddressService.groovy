package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.Address
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.AddressRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class AddressService implements ICoreDataService<Address, Integer> {
    
    @Autowired
    AddressRepository addressRepository
    
    /**
     * Default NoArgs constructor
     */
    AddressService() { }
    
    /**
     * Autowired Constructor
     *
     * @param addressRepository
     */
    AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository
    }
    
    /**
     * Find an individual address using the addresses ID fields
     *
     * @param id the ID fields to search for
     * @return the Address object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Address findById(Integer id) {
        return addressRepository.findOne(id);
    }
    
    /**
     * Find all addresses
     *
     * @return a SearchResult set with the list of Addresses
     */
    @Override
    @Transactional(readOnly = true)
    List<Address> findAll() {
        return addressRepository.findAll();
    }
    
    /**This method saves to either existing object or creates a new object
     * @param id
     * @param line1
     * @param line2
     * @param line3
     * @param line4
     * @param line5
     * @param town
     * @param county
     * @param postcode
     * @param buildingName
     * @param subBuilding
     * @param paon
     * @param saon
     * @return
     */
    @Transactional
    public Address saveAddress(Integer id, String line1, String line2, String line3, String line4,  String line5, String town, String county, String postcode, String buildingName, String subBuilding, String paon, String saon) {
        ValidationUtils.assertNotBlank(line1, "line1 cannot be blank");
        ValidationUtils.assertNotNull(line2, "line2 is mandatory");
        ValidationUtils.assertNotNull(line3, "line3 is mandatory");
        ValidationUtils.assertNotNull(line4, "line4 is mandatory");
        ValidationUtils.assertNotNull(line5, "line5 is mandatory");
        ValidationUtils.assertNotNull(town, "town is mandatory");
        ValidationUtils.assertNotNull(county, "county is mandatory");
        ValidationUtils.assertNotNull(postcode, "postcode is mandatory");
        ValidationUtils.assertNotNull(buildingName, "buildingName is mandatory");
        ValidationUtils.assertNotNull(paon, "paon is mandatory");
        ValidationUtils.assertNotNull(saon, "saon is mandatory");
        
        Address address = null;
        if (id != null) {
            address = findById(id);
            address.setLine1(line1);
            address.setLine2(line2);
            address.setLine3(line3);
            address.setLine4(line4);
            address.setLine5(line5);
            address.setTown(town);
            address.setCounty(county);
            address.setPostcode(postcode);
            address.setBuildingName(buildingName);
            address.setSubBuilding(subBuilding);
            address.setPaon(paon);
            address.setSaon(saon);
            save(address);
        } else {
            address = save(new Address(line1, line2, line3, line4, line5, town, county, postcode, buildingName, subBuilding, paon, saon));
        }
        return address;
    }
    
    /**
     * This service method is used to save a complete Address object in the database
     *
     * @param address the new Address object to be saved
     * @return the saved version of the Address object
     */
    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address)
    }
    
    /**
     * This service method is used to update an Address object in the database from a partial or complete Address object.
     *
     * @param address the partial or complete Address object to be saved
     * @return the saved version of the Address object
     */
    @Transactional
    public Address updateAddress(Address address) {
        Address addressToSave = findById(address.id)
        addressToSave.line1 = address.line1 != null ? address.line1 : addressToSave.line1
        addressToSave.line2 = address.line2 != null ? address.line2 : addressToSave.line2
        addressToSave.line3 = address.line3 != null ? address.line3 : addressToSave.line3
        addressToSave.line4 = address.line4 != null ? address.line4 : addressToSave.line4
        addressToSave.line5 = address.line5 != null ? address.line5 : addressToSave.line5
        addressToSave.town = address.town != null ? address.town : addressToSave.town
        addressToSave.county = address.county != null ? address.county : addressToSave.county
        addressToSave.postcode = address.postcode != null ? address.postcode : addressToSave.postcode
        addressToSave.buildingName = address.buildingName != null ? address.buildingName : addressToSave.buildingName
        addressToSave.subBuilding = address.subBuilding != null ? address.subBuilding : addressToSave.subBuilding
        addressToSave.paon = address.paon != null ? address.paon : addressToSave.paon
        addressToSave.saon = address.saon != null ? address.saon : addressToSave.saon
        return save(addressToSave)
    }
    
    /**
     * Saves a list of Address objects to the database
     *
     * @param sddresses a list of Addresses to be saved to the database
     * @return the list of save Address objects
     */
    @Transactional
    public List<Address> saveAddresses(List<Address> addresses) {
        return addresses.collect { address -> save( address) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Addresses should not be deleted.
     * 
     */
    @Override
    public void delete(Address obj) {
        throw new InvalidOperationException("Addresses should not be deleted.")
    }
}
