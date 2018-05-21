package com.whoiszxl;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.hbase.HBaseConn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HbaseApiTestApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		
		Table table = HBaseConn.getTable("fileTable");
		System.out.println("开始计算："+table.getName());
		
	}

}
