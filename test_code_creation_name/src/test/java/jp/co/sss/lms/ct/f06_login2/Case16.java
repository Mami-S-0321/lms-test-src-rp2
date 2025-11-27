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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
		loginId.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys("StudentAA02");
		login.click();

		//利用規約に遷移したかどうかの確認
		final String loginUser = webDriver
				.findElement(
						By.xpath("/html/body/div[1]/div/div[1]/div/h2"))
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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//現在のパスワード欄が未入力
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		final WebElement submit = webDriver.findElement(By.cssSelector(
				"#upd-form > div.well.bs-component > fieldset > div:nth-child(4) > div > button:nth-child(2)"));

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

		//アラートの変更ボタン押下
		webDriver.findElement(By.id("upd-btn")).click();

		//エラーメッセージが出てるかどうか
		final String error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[1]/div/ul/li/span"))
				.getText();
		assertEquals("現在のパスワードは必須です。", error);

		getEvidence(new Object() {
		}, "2");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");
		//戻るボタンでエラー文のリセット
		webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[4]/div/button[1]"))
				.click();

		//アラートが出るまでの待ち
		Thread.sleep(10000);

		//アラートの要素が画面にあるかどうか
		final WebElement user = webDriver.findElement(By.className("modal-content"));
		final boolean userDisplayed = user.isDisplayed();
		assertTrue(userDisplayed);

		//アラートのキャンセルボタン押下
		webDriver.findElement(By.cssSelector(
				"#div-modal > div.modal-dialog.modal-dialog-center > div > div.modal-footer > button:nth-child(1)"))
				.click();

		//指定位置までスクロール
		scrollBy("-1000");

		//20文字以上のカウント
		String baseText = "Password20";
		int repeatCount = 3;
		String repeatedText = baseText.repeat(repeatCount);

		//パスワード欄へ入力
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		final WebElement submit = webDriver.findElement(By.cssSelector(
				"#upd-form > div.well.bs-component > fieldset > div:nth-child(4) > div > button:nth-child(2)"));
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys(repeatedText);
		passwordConfirm.clear();
		passwordConfirm.sendKeys(repeatedText);

		getEvidence(new Object() {
		}, "1");

		//待ち処理
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		submit.click();

		//アラートが出るまでの待ち
		Thread.sleep(10000);

		//エラーメッセージが出てるかどうか
		final String error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("パスワードの長さが最大値(20)を超えています。", error);

		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() throws InterruptedException {
		// TODO ここに追加 
		//指定位置までスクロール
		scrollBy("1000");
		//戻るボタンでエラー文のリセット
		webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[4]/div/button[1]"))
				.click();

		//指定位置までスクロール
		scrollBy("-1000");

		//ポリシーに合わない変更パスワードを入力
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		final WebElement submit = webDriver.findElement(By.cssSelector(
				"#upd-form > div.well.bs-component > fieldset > div:nth-child(4) > div > button:nth-child(2)"));
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys("password");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("password");

		getEvidence(new Object() {
		}, "1");

		//待ち処理
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		submit.click();

		//エラーメッセージが出てるかどうか
		final String error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。", error);

		getEvidence(new Object() {
		}, "2");

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");
		//戻るボタンでエラー文のリセット
		webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[4]/div/button[1]"))
				.click();

		//待ち処理
		Thread.sleep(10000);

		//一致しない確認パスワードを入力
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		final WebElement submit = webDriver.findElement(By.xpath(
				"/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div[4]/div/button[2]"));
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys("StudentAA0");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("StudentAA000");

		getEvidence(new Object() {
		}, "1");

		//待ち処理
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		submit.click();

		//エラーメッセージが出てるかどうか
		final String error = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("パスワードと確認パスワードが一致しません。", error);

		getEvidence(new Object() {
		}, "2");
	}

}
