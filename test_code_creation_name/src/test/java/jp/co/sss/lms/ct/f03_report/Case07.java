package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//画面遷移
		goTo("http://localhost:8080/lms");

		//タイトルの値とログインボタンの値が一致しているかどうか
		String title = webDriver.getTitle();
		assertEquals("ログイン | LMS", title);

		//ログイン画面のエビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//ログインの際に必要な値取得
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn"));

		//ログインの際に入力する値
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		password.clear();
		password.sendKeys("StudentAA0");
		login.click();

		//ログインできているかどうかの確認
		final String loginUser = webDriver
				.findElement(
						By.cssSelector("#nav-content > ul.nav.navbar-nav.navbar-right > li:nth-child(2) > a > small"))
				.getText();
		assertEquals("ようこそ受講生ＡＡ１さん", loginUser);

		//ログイン後の表示待ち
		visibilityTimeout(By.className("btn"), 10);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws InterruptedException {
		// TODO ここに追加
		//未提出かどうか
		final String submissionCheck = webDriver.findElement(By.cssSelector(
				"#main > div > div:nth-child(6) > div.panel-body > table > tbody > tr:nth-child(1) > td:nth-child(3) > span"))
				.getText();
		assertEquals("未提出", submissionCheck);

		//未提出の研修日の「詳細」ボタンを押下
		final WebElement detail = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[3]/div[2]/table/tbody/tr[1]/td[5]/form/input[3]"));
		detail.click();

		//セクション詳細画面待ち
		Thread.sleep(5000);

		//セクション詳細画面に画面遷移できているかどうか
		final String section = webDriver.findElement(By.className("active")).getText();
		assertEquals("セクション詳細", section);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//「提出する」ボタンを押下
		final WebElement submission = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[5]"));
		submission.click();

		//レポート登録画面待ち
		Thread.sleep(5000);

		//レポート登録画面に遷移できてるかどうか
		final String report = webDriver.findElement(By.tagName("legend")).getText();
		assertEquals("報告レポート", report);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//報告内容を入力
		final WebElement reportInput = webDriver.findElement(By.cssSelector("#content_0"));
		final WebElement submit = webDriver
				.findElement(By.cssSelector("#main > form > div:nth-child(2) > fieldset > div > div > button"));
		reportInput.clear();
		reportInput.sendKeys("報告書を提出致します。");

		getEvidence(new Object() {
		}, "1");

		submit.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//確認ボタン名が「提出済み」に更新されたかどうか
		final WebElement submission = webDriver.findElement(By.cssSelector(
				"#sectionDetail > table > tbody > tr:nth-child(2) > td > form > input.btn.btn-default"));
		String submissionCheck = submission.getAttribute("value");
		assertEquals("提出済み日報【デモ】を確認する", submissionCheck);

		getEvidence(new Object() {
		}, "2");

	}

}
