package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before


import org.junit.Test
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.domain.ilp.Target




public class TargetDtoTest {
    
    private Target target1
    
    private Target target2
    
    private List<Target> targets
    
    ILPInterview createILPInterview() {
        ILPInterview interview = new ILPInterview()
    }
    
    @Before
    public void setup() {
        target1 = new Target(
                id: 1,
                target:'Mr',
                byWhen:'Mr',
                interview: createILPInterview(),
                sendLetter: true
                );
        target2 = new Target(
                id: 2,
                target:'Mrs',
                byWhen:'Mrs',
                interview: createILPInterview(),
                sendLetter: false
                );
        targets = Arrays.asList(target1, target2);
    }
    
    @Test
    public void testMapFromTargetEntity(){
        TargetDto targetTest = TargetDto.mapFromTargetEntity( target1 )
        assertEquals( targetTest.id, target1.id );
        assertEquals( targetTest.target, target1.target);
        assertEquals( targetTest.byWhen, target1.byWhen);
        assertEquals( targetTest.interviewId, target1.interview.id);
        assertEquals( targetTest.sendLetter, target1.sendLetter);
    }
    
    @Test
    public void testMapFromTargetsEntities(){
        List<TargetDto> targetsDtoTest = TargetDto.mapFromTargetsEntities( targets )
        assertEquals( targetsDtoTest[0].id, target1.id );
        assertEquals( targetsDtoTest[0].target, target1.target);
        assertEquals( targetsDtoTest[0].byWhen, target1.byWhen);
        assertEquals( targetsDtoTest[1].id, target2.id );
        assertEquals( targetsDtoTest[1].target, target2.target);
        assertEquals( targetsDtoTest[1].byWhen, target2.byWhen);
    }
    
    
    @Test
    public void testConstructor_Target() {
        TargetDto targetTest = new TargetDto(target1)
        assertEquals( targetTest.id, target1.id );
        assertEquals( targetTest.target, target1.target);
        assertEquals( targetTest.byWhen, target1.byWhen);
        assertEquals( targetTest.interviewId, target1.interview.id);
        assertEquals( targetTest.sendLetter, target1.sendLetter);
    }
}
