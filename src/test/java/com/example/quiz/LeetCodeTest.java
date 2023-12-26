package com.example.quiz;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class LeetCodeTest {
	
	@Test
	public void twoSumTest() {
		int[] nums= {2,7,11,15};
		int target=9;
		Map<Integer, Integer> map=new HashMap<>();
		for(int i=0;i<nums.length;i++) {
			if(map.containsKey(target-nums[i])) {
				System.out.printf("[%d,%d]",map.get(target-nums[i]),i);
				break;
			}
			map.put(nums[i], i);
		}
	}
}
