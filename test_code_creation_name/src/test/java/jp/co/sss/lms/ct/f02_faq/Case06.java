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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		//ログイン後の表示待ち
		visibilityTimeout(By.className("dropdown-toggle"), 10);
		getEvidence(new Object() {
		}, "1");

		//ヘルプリンクを押下
		final WebElement helpButton = webDriver.findElement(
				By.cssSelector("#nav-content > ul:nth-child(1) > li.dropdown.open > ul > li:nth-child(4) > a"));
		helpButton.click();

		//ヘルプ画面に遷移できてるかどうかの確認
		final String help = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("ヘルプ", help);

		//ヘルプ画面表示待ち
		visibilityTimeout(By.tagName("h2"), 10);
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//「よくある質問」リンクボタン押下
		final WebElement question = webDriver
				.findElement(By.cssSelector("#main > div:nth-child(4) > div.panel-body > p > a"));
		question.click();

		Thread.sleep(5000);

		//別タブで表示
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		//よくある質問画面に遷移できてるかどうかの確認
		final String help = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("よくある質問", help);

		//ヘルプ画面表示待ち
		visibilityTimeout(By.tagName("h2"), 10);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//カテゴリ検索を押下
		final WebElement category = webDriver
				.findElement(By.cssSelector("#main > div.well.bs-component > fieldset > ul:nth-child(2) > li > a"));
		category.click();

		//指定位置までスクロール
		scrollBy("1000");

		//検索結果表示待ち
		Thread.sleep(5000);

		//該当カテゴリの検索結果が表示されているかどうか
		final WebElement result = webDriver.findElement(By.className("odd"));
		final boolean resultChecker = result.isDisplayed();
		assertTrue(resultChecker);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() throws InterruptedException {
		// TODO ここに追加
		//検索結果欄を押下し回答を表示
		final WebElement SearchResult = webDriver.findElement(By.className("sorting_1"));
		SearchResult.click();

		//検索結果の回答表示待ち
		Thread.sleep(5000);

		//検索結果の回答が表示されているかどうか
		final WebElement Search = webDriver.findElement(By.className("sorting_1"));
		final boolean SearchResultChecker = Search.isDisplayed();
		assertTrue(SearchResultChecker);

		getEvidence(new Object() {
		});
	}

}
