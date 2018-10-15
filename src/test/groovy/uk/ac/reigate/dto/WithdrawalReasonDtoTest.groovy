package uk.ac.reigate.dto;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilr.WithdrawalReason

public class WithdrawalReasonDtoTest {
    
    private WithdrawalReason withdrawalReason1
    
    private WithdrawalReason withdrawalReason2
    
    private List<WithdrawalReason> withdrawalReasons
    
    @Before
    public void setup() {
        withdrawalReason1 = new WithdrawalReason(
                id: 1,
                code: 'UK',
                description: 'United Kingdom',
                shortDescription:'United Kingdom',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        withdrawalReason2 = new WithdrawalReason(
                id: 2,
                code: 'EU',
                description: 'European Ecconomical Union',
                shortDescription:'European Union',
                validFrom: new Date().parse('yyyy/MM/dd', '2011/07/09'),
                validTo: new Date().parse('yyyy/MM/dd', '2013/07/09')
                );
        withdrawalReasons = Arrays.asList(withdrawalReason1, withdrawalReason2);
    }
    
    @Test
    public void testMapFromWithdrawalReasonEntityTest() {
        WithdrawalReasonDto withdrawalReasonTest = WithdrawalReasonDto.mapFromWithdrawalReasonEntity( withdrawalReason1 )
        assertEquals( withdrawalReasonTest.id, withdrawalReason1.id);
        assertEquals( withdrawalReasonTest.code, withdrawalReason1.code);
        assertEquals( withdrawalReasonTest.description, withdrawalReason1.description);
        assertEquals( withdrawalReasonTest.shortDescription, withdrawalReason1.shortDescription);
        assertEquals( withdrawalReasonTest.validFrom, withdrawalReason1.validFrom);
        assertEquals( withdrawalReasonTest.validTo, withdrawalReason1.validTo);
    }
    
    @Test
    public void testMapFromWithdrawalReasonsEntitiesTest(){
        List<WithdrawalReasonDto> withdrawalReasonsDtoTest = WithdrawalReasonDto.mapFromWithdrawalReasonsEntities(withdrawalReasons)
        assertEquals( withdrawalReasonsDtoTest[0].id, withdrawalReason1.id );
        assertEquals( withdrawalReasonsDtoTest[0].code, withdrawalReason1.code );
        assertEquals( withdrawalReasonsDtoTest[0].description, withdrawalReason1.description);
        assertEquals( withdrawalReasonsDtoTest[0].shortDescription, withdrawalReason1.shortDescription);
        assertEquals( withdrawalReasonsDtoTest[0].validFrom, withdrawalReason1.validFrom);
        assertEquals( withdrawalReasonsDtoTest[0].validTo, withdrawalReason1.validTo);
        assertEquals( withdrawalReasonsDtoTest[1].id, withdrawalReason2.id );
        assertEquals( withdrawalReasonsDtoTest[1].code, withdrawalReason2.code );
        assertEquals( withdrawalReasonsDtoTest[1].description, withdrawalReason2.description);
        assertEquals( withdrawalReasonsDtoTest[1].shortDescription, withdrawalReason2.shortDescription);
        assertEquals( withdrawalReasonsDtoTest[1].validFrom, withdrawalReason2.validFrom);
        assertEquals( withdrawalReasonsDtoTest[1].validTo, withdrawalReason2.validTo);
    }
    
    @Test
    public void testMapToWithdrawalReasonEntityTest(){
        WithdrawalReasonDto withdrawalReasonDto = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        WithdrawalReason withdrawalReason = WithdrawalReasonDto.mapToWithdrawalReasonEntity( withdrawalReasonDto )
        assertEquals( withdrawalReason.id, withdrawalReason1.id );
        assertEquals( withdrawalReason.code, withdrawalReason1.code);
        assertEquals( withdrawalReason.description, withdrawalReason1.description);
        assertEquals( withdrawalReason.shortDescription, withdrawalReason1.shortDescription);
        assertEquals( withdrawalReason.validFrom, withdrawalReason1.validFrom);
        assertEquals( withdrawalReason.validTo, withdrawalReason1.validTo);
    }
    
    @Test
    public void testEquals_Same() {
        WithdrawalReasonDto withdrawalReasonDto1 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        WithdrawalReasonDto withdrawalReasonDto2 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        assertEquals(withdrawalReasonDto1, withdrawalReasonDto2)
    }
    
    @Test
    public void testEquals_Different() {
        WithdrawalReasonDto withdrawalReasonDto1 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        WithdrawalReasonDto withdrawalReasonDto2 = new WithdrawalReasonDto(withdrawalReason2.id, withdrawalReason2.code, withdrawalReason2.description, withdrawalReason2.shortDescription, withdrawalReason2.validFrom, withdrawalReason2.validTo)
        assertNotEquals(withdrawalReasonDto1, withdrawalReasonDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        WithdrawalReasonDto withdrawalReasonDto1 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason2.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        WithdrawalReasonDto withdrawalReasonDto2 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason2.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        assertEquals(withdrawalReasonDto1.hashCode(), withdrawalReasonDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        WithdrawalReasonDto withdrawalReasonDto1 = new WithdrawalReasonDto(withdrawalReason1.id, withdrawalReason1.code, withdrawalReason1.description, withdrawalReason1.shortDescription, withdrawalReason1.validFrom, withdrawalReason1.validTo)
        WithdrawalReasonDto withdrawalReasonDto2 = new WithdrawalReasonDto(withdrawalReason2.id, withdrawalReason2.code, withdrawalReason2.description, withdrawalReason2.shortDescription, withdrawalReason2.validFrom, withdrawalReason2.validTo)
        assertNotEquals(withdrawalReasonDto1.hashCode(), withdrawalReasonDto2.hashCode())
    }
    
    @Test
    public void testConstructor_WithdrawalReason() {
        WithdrawalReasonDto withdrawalReasonDto = new WithdrawalReasonDto(withdrawalReason1)
        assertEquals( withdrawalReasonDto.code, withdrawalReason1.code )
        assertEquals( withdrawalReasonDto.description, withdrawalReason1.description )
        assertEquals( withdrawalReasonDto.shortDescription, withdrawalReason1.shortDescription )
        assertEquals( withdrawalReasonDto.validFrom, withdrawalReason1.validFrom )
        assertEquals( withdrawalReasonDto.validTo, withdrawalReason1.validTo )
    }
}
