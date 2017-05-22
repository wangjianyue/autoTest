package wjy.auto.test;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)  //ʹ��junit4���в���   
@ContextConfiguration({"classpath:spring/spring-code-create.xml"}) //���������ļ�   
public class BaseTest extends TestCase{

}
