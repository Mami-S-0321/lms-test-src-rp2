package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		password.sendKeys("StudentAA01");
		login.click();

		//ログインできているかどうかの確認
		final String loginUser = webDriver.findElement(By.tagName("small")).getText();
		assertEquals("ようこそ受講生ＡＡ１さん", loginUser);

		//ログイン後の表示待ち
		visibilityTimeout(By.className("btn"), 10);
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		//ヘルプリンクに行くまでの機能ボタン押下
		final WebElement toggle = webDriver.findElement(By.className("dropdown-toggle"));
		toggle.click();

		//ヘルプリンクを押下
		final WebElement helpButton = webDriver.findElement(By.cssSelector("a"));
		helpButton.click();

		//ヘルプ画面遷移
		goTo("http://localhost:8080/lms/help");

		//ヘルプ画面に遷移できてるかどうかの確認
		final String help = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("ヘルプ", help);

		//ヘルプ画面表示待ち
		visibilityTimeout(By.tagName("h2"), 10);
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		//「よくある質問」リンクボタン押下
		final WebElement question = webDriver.findElement(By.cssSelector("a"));
		question.click();

		//「よくある質問」画面遷移
		goTo("http://localhost:8080/lms/faq");

		//よくある質問」画面に遷移できてるかどうかの確認
		final String help = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("よくある質問", help);

		//ヘルプ画面表示待ち
		visibilityTimeout(By.tagName("h2"), 10);
		getEvidence(new Object() {
		});

	}

}
