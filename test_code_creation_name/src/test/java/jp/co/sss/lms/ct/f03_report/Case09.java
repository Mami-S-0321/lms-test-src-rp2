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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() throws InterruptedException {
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
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		getEvidence(new Object() {
		}, "1");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//レポート登録画面に遷移できたかどうか
		final String userDetails = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2")).getText();
		assertEquals("週報【デモ】 2022年10月2日", userDetails);

		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//報告内容[学習項目]を修正
		final WebElement LearningItems = webDriver.findElement(
				By.xpath("//*[@id=\"intFieldName_0\"]"));
		LearningItems.clear();

		getEvidence(new Object() {
		}, "1");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[1]/fieldset/div/div[1]/p/span")).getText();
		assertEquals("* 理解度を入力した場合は、学習項目は必須です。", Error);

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール(値を元に戻すため)
		scrollBy("-1000");

		final WebElement LearningItemsReset = webDriver.findElement(
				By.xpath("//*[@id=\"intFieldName_0\"]"));
		LearningItemsReset.sendKeys("ITリテラシー①");
		LearningItemsReset.sendKeys(Keys.ENTER);

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() throws InterruptedException {
		// TODO ここに追加
		//報告内容[理解度]を修正
		WebElement selectElement = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div/div[2]/select"));
		Select select = new Select(selectElement);
		select.selectByIndex(0);

		//報告内容[理解度]を修正待ち
		visibilityTimeout(By.xpath("//*[@id=\"content_0\"]"), 10);
		getEvidence(new Object() {
		}, "1");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div/div[2]/p/span"))
				.getText();
		assertEquals("* 学習項目を入力した場合は、理解度は必須です。", Error);

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール(値を元に戻すため)
		scrollBy("-1000");
		final WebElement ComprehensionLevelReset = webDriver.findElement(
				By.xpath("//*[@id=\"intFieldValue_0\"]"));
		ComprehensionLevelReset.click();

		final WebElement ResetLevel2 = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[1]/fieldset/div/div[2]/select/option[3]"));
		ResetLevel2.click();

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submissionReset = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submissionReset.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("300");
		Thread.sleep(5000);
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 50);
		getEvidence(new Object() {
		}, "1");

		//目標の達成度が数値以外
		final WebElement GoalAchievement = webDriver.findElement(
				By.xpath("//*[@id=\"content_0\"]"));
		GoalAchievement.clear();
		GoalAchievement.sendKeys("a");

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		//指定位置までスクロール
		scrollBy("300");
		Thread.sleep(10000);
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 60);

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"))
				.getText();
		assertEquals("* 目標の達成度は半角数字で入力してください。", Error);

		getEvidence(new Object() {
		}, "3");

		//指定位置までスクロール(値を元に戻すため)
		scrollBy("-1000");
		final WebElement GoalAchievementReset = webDriver.findElement(
				By.xpath("//*[@id=\"content_0\"]"));
		GoalAchievementReset.clear();
		GoalAchievementReset.sendKeys("5");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submissionReset = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submissionReset.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("300");
		Thread.sleep(5000);
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 50);
		getEvidence(new Object() {
		}, "1");

		//目標の達成度が範囲外
		final WebElement GoalAchievement = webDriver.findElement(
				By.xpath("//*[@id=\"content_0\"]"));
		GoalAchievement.clear();
		GoalAchievement.sendKeys("15");

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		//指定位置までスクロール
		scrollBy("300");
		Thread.sleep(5000);
		visibilityTimeout(By.xpath("//*[@id=\"content_1\"]"), 50);

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[1]/div/p/span"))
				.getText();
		assertEquals("* 目標の達成度は、半角数字で、1～10の範囲内で入力してください。", Error);

		getEvidence(new Object() {
		}, "3");

		//指定位置までスクロール(値を元に戻すため)
		scrollBy("-1000");
		final WebElement GoalAchievementReset = webDriver.findElement(
				By.xpath("//*[@id=\"content_0\"]"));
		GoalAchievementReset.clear();
		GoalAchievementReset.sendKeys("5");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submissionReset = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submissionReset.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("300");
		Thread.sleep(5000);
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 50);

		getEvidence(new Object() {
		}, "1");

		//目標の達成度・所感が未入力
		final WebElement GoalAchievement = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[1]/div/textarea"));
		final WebElement Impressions = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"));
		GoalAchievement.clear();
		Impressions.clear();

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール
		scrollBy("1000");
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"), 50);

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("300");
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/p/span"), 50);

		//エラーが出ているかどうか
		final String GoalAchievementError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[1]/div/p/span"))
				.getText();
		assertEquals("* 目標の達成度は半角数字で入力してください。", GoalAchievementError);

		final String ImpressionsError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/p/span"))
				.getText();
		assertEquals("* 所感は必須です。", ImpressionsError);

		getEvidence(new Object() {
		}, "3");

		//値を元に戻すため
		scrollBy("300");
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 20);
		final WebElement GoalAchievementReset = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[1]/div/textarea"));
		GoalAchievementReset.clear();
		GoalAchievementReset.sendKeys("5");
		final WebElement LearningItemsReset = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"));
		LearningItemsReset.clear();
		LearningItemsReset.sendKeys("週報のサンプルです。");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement Resetsubmission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		Resetsubmission.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//該当レポートの「修正する」ボタンを押下
		final WebElement Fixes = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		Fixes.click();
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");
		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"), 10);

		getEvidence(new Object() {
		}, "1");

		//2000文字以上のカウント
		String baseText = "2000文字以上ループ";
		int repeatCount = 200;
		String repeatedText = baseText.repeat(repeatCount);

		//所感・一週間の振り返りが2000文字超の入力
		final WebElement LearningItems = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/textarea"));
		final WebElement Impressions = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[3]/div/textarea"));
		LearningItems.clear();
		LearningItems.sendKeys(repeatedText);
		Impressions.clear();
		Impressions.sendKeys(repeatedText);

		//入力完了からエビデンスまでの待ち
		Thread.sleep(5000);

		getEvidence(new Object() {
		}, "2");

		//指定位置までスクロール
		scrollBy("1000");

		//「提出する」ボタンを押下
		final WebElement submission = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/form/div[3]/fieldset/div/div/button"));
		submission.click();

		//画面待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//エラーが出ているかどうか
		final String LearningItemsError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/p/span"))
				.getText();
		assertEquals("* 所感の長さが最大値(2000)を超えています。", LearningItemsError);

		final String ImpressionsError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[3]/div/p/span"))
				.getText();
		assertEquals("* 一週間の振り返りの長さが最大値(2000)を超えています。", ImpressionsError);

		visibilityTimeout(By.xpath("/html/body/div[1]/div/div[1]/div/form/div[2]/fieldset/div[2]/div/p/span"), 10);
		getEvidence(new Object() {
		}, "3");
	}

}
