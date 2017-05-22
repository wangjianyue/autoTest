/**
 * @author wjy
 * create at 2015-6-12
 */
package com.example.aop;

import java.util.List;
import java.util.Map;

import com.auto.test.annotation.Mock;

/**
 * @author wjy
 * edit at 2015-6-12
 */
public interface AService {
	@Mock
    public List<String> barA(String _msg, int _type);
    @Mock
	public int[] fooA(String[] a, Map<String, Integer> b);
}
