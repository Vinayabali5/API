package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.FundingModel

public class FundingModelDtoTest {
    
    private FundingModel fundingModel1
    
    private FundingModel fundingModel2
    
    private List<FundingModel> fundingModels
    
    @Before
    public void setup() {
        fundingModel1 = new FundingModel(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        fundingModel2 = new FundingModel(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        fundingModels = Arrays.asList(fundingModel1, fundingModel2);
    }
    
    @Test
    public void testMapFromFundingModelEntityTest() {
        FundingModelDto fundingModelTest = FundingModelDto.mapFromFundingModelEntity( fundingModel1 )
        assertEquals( fundingModelTest.id, fundingModel1.id);
        assertEquals( fundingModelTest.code, fundingModel1.code);
        assertEquals( fundingModelTest.description, fundingModel1.description);
        assertEquals( fundingModelTest.shortDescription, fundingModel1.shortDescription);
        assertEquals( fundingModelTest.validFrom, fundingModel1.validFrom);
        assertEquals( fundingModelTest.validTo, fundingModel1.validTo);
    }
    
    @Test
    public void testMapFromFundingModelsEntitiesTest(){
        List<FundingModelDto> fundingModelsDtoTest = FundingModelDto.mapFromFundingModelsEntities(fundingModels)
        assertEquals( fundingModelsDtoTest[0].id, fundingModel1.id );
        assertEquals( fundingModelsDtoTest[0].code, fundingModel1.code );
        assertEquals( fundingModelsDtoTest[0].description, fundingModel1.description);
        assertEquals( fundingModelsDtoTest[0].shortDescription, fundingModel1.shortDescription);
        assertEquals( fundingModelsDtoTest[0].validFrom, fundingModel1.validFrom);
        assertEquals( fundingModelsDtoTest[0].validTo, fundingModel1.validTo);
        assertEquals( fundingModelsDtoTest[1].id, fundingModel2.id );
        assertEquals( fundingModelsDtoTest[1].code, fundingModel2.code );
        assertEquals( fundingModelsDtoTest[1].description, fundingModel2.description);
        assertEquals( fundingModelsDtoTest[1].shortDescription, fundingModel2.shortDescription);
        assertEquals( fundingModelsDtoTest[1].validFrom, fundingModel2.validFrom);
        assertEquals( fundingModelsDtoTest[1].validTo, fundingModel2.validTo);
    }
    
    @Test
    public void testMapToFundingModelEntityTest(){
        FundingModelDto fundingModelDto = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        FundingModel fundingModel = FundingModelDto.mapToFundingModelEntity( fundingModelDto )
        assertEquals( fundingModel.id, fundingModel1.id );
        assertEquals( fundingModel.code, fundingModel1.code);
        assertEquals( fundingModel.description, fundingModel1.description);
        assertEquals( fundingModel.shortDescription, fundingModel1.shortDescription);
        assertEquals( fundingModel.validFrom, fundingModel1.validFrom);
        assertEquals( fundingModel.validTo, fundingModel1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        FundingModelDto fundingModelDto1 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        FundingModelDto fundingModelDto2 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        assertEquals(fundingModelDto1, fundingModelDto2)
    }
    
    @Test
    public void testEquals_Different() {
        FundingModelDto fundingModelDto1 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        FundingModelDto fundingModelDto2 = new FundingModelDto(fundingModel2.id, fundingModel2.code, fundingModel2.description, fundingModel2.shortDescription, fundingModel2.validFrom, fundingModel2.validTo)
        assertNotEquals(fundingModelDto1, fundingModelDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        FundingModelDto fundingModelDto1 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel2.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        FundingModelDto fundingModelDto2 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel2.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        assertEquals(fundingModelDto1.hashCode(), fundingModelDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        FundingModelDto fundingModelDto1 = new FundingModelDto(fundingModel1.id, fundingModel1.code, fundingModel1.description, fundingModel1.shortDescription, fundingModel1.validFrom, fundingModel1.validTo)
        FundingModelDto fundingModelDto2 = new FundingModelDto(fundingModel2.id, fundingModel2.code, fundingModel2.description, fundingModel2.shortDescription, fundingModel2.validFrom, fundingModel2.validTo)
        assertNotEquals(fundingModelDto1.hashCode(), fundingModelDto2.hashCode())
    }
    
    @Test
    public void testConstructor_FundingModel() {
        FundingModelDto fundingModelDto = new FundingModelDto(fundingModel1)
        assertEquals( fundingModelDto.code, fundingModel1.code )
        assertEquals( fundingModelDto.description, fundingModel1.description )
        assertEquals( fundingModelDto.shortDescription, fundingModel1.shortDescription )
        assertEquals( fundingModelDto.validFrom, fundingModel1.validFrom )
        assertEquals( fundingModelDto.validTo, fundingModel1.validTo )
    }
}
