package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.utils.ChineseUtils;

public class ChineseUtilsTest {

	@Test
	public void testGettingEnumData(){
		assertEquals("A9" , ChineseUtils.getAllFirstLetter("阿9"));
		assertEquals("B8" , ChineseUtils.getAllFirstLetter("叭8"));
		assertEquals("C7" , ChineseUtils.getAllFirstLetter("串7"));
		assertEquals("D3" , ChineseUtils.getAllFirstLetter("定3"));
		assertEquals("E1" , ChineseUtils.getAllFirstLetter("恶1"));
		assertEquals("F12" , ChineseUtils.getAllFirstLetter("发12"));
		assertEquals("G5" , ChineseUtils.getAllFirstLetter("个5"));
		assertEquals("H2" , ChineseUtils.getAllFirstLetter("和2"));
		assertEquals("J1" , ChineseUtils.getAllFirstLetter("家1"));
		assertEquals("K8" , ChineseUtils.getAllFirstLetter("看8"));
		assertEquals("L1" , ChineseUtils.getAllFirstLetter("了1"));
		assertEquals("M5" , ChineseUtils.getAllFirstLetter("吗5"));
		assertEquals("N7" , ChineseUtils.getAllFirstLetter("你7"));
		assertEquals("O7" , ChineseUtils.getAllFirstLetter("欧7"));
		assertEquals("P9" , ChineseUtils.getAllFirstLetter("片9"));
		assertEquals("Q5" , ChineseUtils.getAllFirstLetter("区5"));
		assertEquals("R4" , ChineseUtils.getAllFirstLetter("人4"));
		assertEquals("S4" , ChineseUtils.getAllFirstLetter("死4"));
		assertEquals("T7" , ChineseUtils.getAllFirstLetter("天7"));
		assertEquals("W3" , ChineseUtils.getAllFirstLetter("问3"));
		assertEquals("X5" , ChineseUtils.getAllFirstLetter("下5"));
		assertEquals("Y3" , ChineseUtils.getAllFirstLetter("有3"));
		assertEquals("Z1" , ChineseUtils.getAllFirstLetter("字1"));
		

		assertEquals("As" , ChineseUtils.getAllFirstLetter("阿s"));
		assertEquals("Ba" , ChineseUtils.getAllFirstLetter("叭a"));
		assertEquals("Cs" , ChineseUtils.getAllFirstLetter("串s"));
		assertEquals("Dd" , ChineseUtils.getAllFirstLetter("定d"));
		assertEquals("Eh" , ChineseUtils.getAllFirstLetter("恶h"));
		assertEquals("Fa" , ChineseUtils.getAllFirstLetter("发a"));
		assertEquals("Gb" , ChineseUtils.getAllFirstLetter("个b"));
		assertEquals("Hr" , ChineseUtils.getAllFirstLetter("和r"));
		assertEquals("Jd" , ChineseUtils.getAllFirstLetter("家d"));
		assertEquals("Kj" , ChineseUtils.getAllFirstLetter("看j"));
		assertEquals("Lj" , ChineseUtils.getAllFirstLetter("了j"));
		assertEquals("Mv" , ChineseUtils.getAllFirstLetter("吗v"));
		assertEquals("Ns" , ChineseUtils.getAllFirstLetter("你s"));
		assertEquals("Oz" , ChineseUtils.getAllFirstLetter("欧z"));
		assertEquals("Pz" , ChineseUtils.getAllFirstLetter("片z"));
		assertEquals("Qz" , ChineseUtils.getAllFirstLetter("区z"));
		assertEquals("Rx" , ChineseUtils.getAllFirstLetter("人x"));
		assertEquals("Sh" , ChineseUtils.getAllFirstLetter("死h"));
		assertEquals("Tg" , ChineseUtils.getAllFirstLetter("天g"));
		assertEquals("Ww" , ChineseUtils.getAllFirstLetter("问w"));
		assertEquals("Xl" , ChineseUtils.getAllFirstLetter("下l"));
		assertEquals("Yu" , ChineseUtils.getAllFirstLetter("有u"));
		assertEquals("Zo" , ChineseUtils.getAllFirstLetter("字o"));
		


		assertEquals("4A" , ChineseUtils.getAllFirstLetter("4阿"));
		assertEquals("3B" , ChineseUtils.getAllFirstLetter("3叭"));
		assertEquals("2C" , ChineseUtils.getAllFirstLetter("2串"));
		assertEquals("5D" , ChineseUtils.getAllFirstLetter("5定"));
		assertEquals("8E" , ChineseUtils.getAllFirstLetter("8恶"));
		assertEquals("9F" , ChineseUtils.getAllFirstLetter("9发"));
		assertEquals("0G" , ChineseUtils.getAllFirstLetter("0个"));
		assertEquals("88H" , ChineseUtils.getAllFirstLetter("88和"));
		assertEquals("7J" , ChineseUtils.getAllFirstLetter("7家"));
		assertEquals("5K" , ChineseUtils.getAllFirstLetter("5看"));
		assertEquals("4L" , ChineseUtils.getAllFirstLetter("4了"));
		assertEquals("3M" , ChineseUtils.getAllFirstLetter("3吗"));
		assertEquals("2N" , ChineseUtils.getAllFirstLetter("2你"));
		assertEquals("1O" , ChineseUtils.getAllFirstLetter("1欧"));
		assertEquals("3P" , ChineseUtils.getAllFirstLetter("3片"));
		assertEquals("2Q" , ChineseUtils.getAllFirstLetter("2区"));
		assertEquals("3R" , ChineseUtils.getAllFirstLetter("3人"));
		assertEquals("5S" , ChineseUtils.getAllFirstLetter("5死"));
		assertEquals("7T" , ChineseUtils.getAllFirstLetter("7天"));
		assertEquals("8W" , ChineseUtils.getAllFirstLetter("8问"));
		assertEquals("2X" , ChineseUtils.getAllFirstLetter("2下"));
		assertEquals("1Y" , ChineseUtils.getAllFirstLetter("1有"));
		assertEquals("4Z" , ChineseUtils.getAllFirstLetter("4字"));
		


		assertEquals("xA" , ChineseUtils.getAllFirstLetter("x阿"));
		assertEquals("cB" , ChineseUtils.getAllFirstLetter("c叭"));
		assertEquals("vC" , ChineseUtils.getAllFirstLetter("v串"));
		assertEquals("nD" , ChineseUtils.getAllFirstLetter("n定"));
		assertEquals("rE" , ChineseUtils.getAllFirstLetter("r恶"));
		assertEquals("gF" , ChineseUtils.getAllFirstLetter("g发"));
		assertEquals("lG" , ChineseUtils.getAllFirstLetter("l个"));
		assertEquals("jH" , ChineseUtils.getAllFirstLetter("j和"));
		assertEquals("hJ" , ChineseUtils.getAllFirstLetter("h家"));
		assertEquals("sK" , ChineseUtils.getAllFirstLetter("s看"));
		assertEquals("sL" , ChineseUtils.getAllFirstLetter("s了"));
		assertEquals("dM" , ChineseUtils.getAllFirstLetter("d吗"));
		assertEquals("sN" , ChineseUtils.getAllFirstLetter("s你"));
		assertEquals("rO" , ChineseUtils.getAllFirstLetter("r欧"));
		assertEquals("kP" , ChineseUtils.getAllFirstLetter("k片"));
		assertEquals("kQ" , ChineseUtils.getAllFirstLetter("k区"));
		assertEquals("hR" , ChineseUtils.getAllFirstLetter("h人"));
		assertEquals("qS" , ChineseUtils.getAllFirstLetter("q死"));
		assertEquals("sT" , ChineseUtils.getAllFirstLetter("s天"));
		assertEquals("fW" , ChineseUtils.getAllFirstLetter("f问"));
		assertEquals("gX" , ChineseUtils.getAllFirstLetter("g下"));
		assertEquals("eY" , ChineseUtils.getAllFirstLetter("e有"));
		assertEquals("sZ" , ChineseUtils.getAllFirstLetter("s字"));

		assertEquals("AHGD2323123HZC" , ChineseUtils.getAllFirstLetter("阿回给定2323123汉字串"));
		assertEquals("AHGDss，HZC" , ChineseUtils.getAllFirstLetter("阿回给定ss，汉字串"));
		assertEquals("AHGDss，asdHZC" , ChineseUtils.getAllFirstLetter("阿回给定ss，asd汉字串"));
	}
}
