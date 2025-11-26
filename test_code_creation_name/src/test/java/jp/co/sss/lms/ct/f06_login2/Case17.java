package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//ログインの際に必要な値取得
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn"));

		//ログインの際に入力する値
		loginId.clear();
		loginId.sendKeys("StudentAA05");
		password.clear();
		password.sendKeys("StudentAA05");
		login.click();

		//利用規約に遷移したかどうかの確認
		final String loginUser = webDriver
				.findElement(
						By.cssSelector("#main > h2"))
				.getText();
		assertEquals("利用規約", loginUser);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("500");

		//同意します」チェックボックスにチェックを入れ「次へ」ボタン押下
		final WebElement checkBox = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[1]/div/label/input[1]"));
		final WebElement submit = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[2]/button"));
		checkBox.click();
		getEvidence(new Object() {
		}, "1");
		submit.click();

		//パスワード変更画面に遷移したかどうか
		final String passwordChange = webDriver.findElement(By.cssSelector("#main > h2")).getText();
		assertEquals("パスワード変更", passwordChange);

		getEvidence(new Object() {
		}, "2");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//変更パスワードの際に必要な値取得
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		final WebElement submit = webDriver.findElement(By.cssSelector(
				"#upd-form > div.well.bs-component > fieldset > div:nth-child(4) > div > button:nth-child(2)"));

		//変更パスワードの際に入力する値
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA05");
		password.clear();
		password.sendKeys("StudentAA0");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("StudentAA0");

		getEvidence(new Object() {
		}, "1");

		//指定位置までスクロール
		scrollBy("500");

		submit.click();

		//アラートが出るまでの待ち
		Thread.sleep(10000);

		//アラートの要素が画面にあるかどうか
		final WebElement user = webDriver.findElement(By.className("modal-content"));
		final boolean userDisplayed = user.isDisplayed();
		assertTrue(userDisplayed);

		getEvidence(new Object() {
		}, "2");

		//アラートの変更ボタン押下
		webDriver.findElement(By.id("upd-btn")).click();

		//コース詳細画面に遷移待ち
		Thread.sleep(10000);

		//コース詳細画面に遷移できているかどうか
		final String loginUser = webDriver
				.findElement(
						By.cssSelector("#nav-content > ul.nav.navbar-nav.navbar-right > li:nth-child(2) > a > small"))
				.getText();
		assertEquals("ようこそ受講生ＡＡ５さん", loginUser);

		getEvidence(new Object() {
		}, "3");

	}

}
