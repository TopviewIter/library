package com.xluo.util;

import org.junit.Assert;
import org.junit.Test;


public class DBUtilTest {

	@Test
	public void testGetCon() {
		Assert.assertEquals(true, DBUtil.getCon() != null);
	}

}
