package uk.ac.reigate.services

import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.ac.reigate.domain.Address
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.AddressRepository
import uk.ac.reigate.services.AddressService;


class AddressServiceTest {
    
    private AddressRepository addressRepository
    
    private AddressService addressService;
    
    Address address1
    Address address2
    
    
    @Before
    public void setup() {
        this.addressRepository = Mockito.mock(AddressRepository.class);
        this.addressService = new AddressService(addressRepository);
        
        address1 = new Address(
                id: 1,
                line1: '309',
                line2: 'Mercury House',
                line3: 'jude street',
                line4: null,
                line5: null,
                town: 'canning',
                county: null,
                postcode: 'E161FF',
                buildingName: null,
                subBuilding: null,
                paon: null,
                saon: null
                )
        address2 = new Address(
                id: 2,
                line1: '309',
                line2: 'Mercury House',
                line3: 'jude street',
                line4: null,
                line5: null,
                town: 'canning',
                county: null,
                postcode: 'E161FF',
                buildingName: null,
                subBuilding: null,
                paon: null,
                saon: null
                );
        
        when(addressRepository.findAll()).thenReturn([address1, address2]);
        when(addressRepository.findOne(1)).thenReturn(address1);
        when(addressRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(addressRepository.save(any(Address.class))).thenReturn(address1);
    }
    
    @Test
    public void testFindAddresses() {
        List<Address> result = addressService.findAll();
        verify(addressRepository, times(1)).findAll()
        verifyNoMoreInteractions(addressRepository)
    }
    
    @Test
    public void testFindAddress() {
        Address result = addressService.findById(1);
        verify(addressRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(addressRepository)
    }
    
    @Test
    public void testSaveNewAddress() {
        Address savedAddress = addressService.save(address1);
        verify(addressRepository, times(1)).save(any())
        verifyNoMoreInteractions(addressRepository)
    }
    
    @Test
    public void testSaveAddress() {
        Address savedAddress = addressService.save(address1);
        verify(addressRepository, times(1)).save(any())
        verifyNoMoreInteractions(addressRepository)
    }
    
    @Test
    public void testSaveAddresses() {
        List<Address> savedAddresses = addressService.saveAddresses([address1, address2]);
        verify(addressRepository, times(2)).save(any(Address.class))
        verifyNoMoreInteractions(addressRepository)
    }
}

