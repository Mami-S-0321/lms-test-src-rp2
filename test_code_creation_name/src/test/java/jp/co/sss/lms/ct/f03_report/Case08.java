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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws InterruptedException {
		// TODO ここに追加
		//提出済みかどうか
		final String submissionCheck = webDriver.findElement(By.cssSelector(
				"#main > div > div:nth-child(5) > div.panel-body > table > tbody > tr:nth-child(2) > td:nth-child(3) > span"))
				.getText();
		assertEquals("提出済み", submissionCheck);

		//提出済みの研修日の「詳細」ボタンを押下
		final WebElement detail = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
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
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		getEvidence(new Object() {
		}, "1");

		//「提出済み週報【デモ】を確認する」ボタンを押下
		final WebElement DailyReportcheck = webDriver
				.findElement(By.cssSelector(
						"#sectionDetail > table:nth-child(7) > tbody > tr:nth-child(3) > td > form > input.btn.btn-default"));
		DailyReportcheck.click();

		//週報【デモ】 画面待ち
		Thread.sleep(5000);

		//週報【デモ】 画面に遷移できてるかどうか
		final String report = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2")).getText();
		assertEquals("週報【デモ】 2022年10月2日", report);

		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");
		
		//修正前のエビデンス取得
		getEvidence(new Object() {
		}, "1");

		//所感を修正
		final WebElement ReportEditing = webDriver.findElement(By.cssSelector("#content_1"));
		final WebElement submit = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));

		ReportEditing.clear();
		ReportEditing.sendKeys("週報のサンプルです。");
		ReportEditing.sendKeys(Keys.ENTER);
		ReportEditing.sendKeys("報告書を追記しました。");

		getEvidence(new Object() {
		}, "2");

		submit.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//セクション詳細画面に画面遷移できているかどうか
		final String section = webDriver.findElement(By.xpath("//*[@id=\"main\"]/ol/li[2]")).getText();
		assertEquals("セクション詳細", section);

		getEvidence(new Object() {
		}, "3");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() throws InterruptedException {
		// TODO ここに追加
		//上部メニューの「ようこそ○○さん」リンクを押下
		final WebElement user = webDriver.findElement(
				By.cssSelector("#nav-content > ul.nav.navbar-nav.navbar-right > li:nth-child(2) > a > small"));
		user.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//ユーザー詳細画面に遷移できたかどうか
		final String userDetails = webDriver.findElement(By.cssSelector("#main > h2")).getText();
		assertEquals("ユーザー詳細", userDetails);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		getEvidence(new Object() {
		}, "1");

		//該当レポートの「詳細」ボタンを押下
		final WebElement ReportDetail = webDriver.findElement(By.xpath(
				"//*[@id=\"main\"]/table[3]/tbody/tr[3]/td[5]/form[1]/input[1]"));
		ReportDetail.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("-1000");

		//レポート詳細画面で修正内容を確認
		final boolean EdtingCheak = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[2]/td"))
				.getText().contains("報告書を追記しました。");
		assertTrue(EdtingCheak);

		getEvidence(new Object() {
		}, "2");
	}

}
