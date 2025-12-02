package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース14
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果50点")
public class Case14 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		//「試験有」の研修日の「詳細」ボタンを押下
		final WebElement ExamAvailable = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
		ExamAvailable.click();

		//セクション詳細画面に遷移してるかどうか
		final String section = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/section/detail", section);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		// TODO ここに追加
		//「本日の試験」エリアの「詳細」ボタンを押下
		final WebElement DetailsButton = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/div/div/table[1]/tbody/tr[2]/td[2]/form/input[1]"));
		DetailsButton.click();

		//試験開始画面に遷移できてるかどうか
		final String section = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/start", section);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//「試験を開始する」ボタンを押下
		final WebElement TestStartButton = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/div/form/input[4]"));
		TestStartButton.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//試験問題画面に遷移できてるかどうか
		final String section = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/form[1]/div[1]/div[1]")).getText();
		assertEquals("第1問 【】", section);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 正答と誤答が半々で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		// TODO ここに追加
		//第１問
		WebElement answer1 = webDriver.findElement(By.xpath("//*[@id=\"answer-0-2\"]"));
		answer1.click();
		scrollBy("400");
		//第２問
		WebElement answer2 = webDriver.findElement(By.xpath("//*[@id=\"answer-1-2\"]"));
		answer2.click();
		scrollBy("400");
		//第３問
		WebElement answer3 = webDriver.findElement(By.xpath("//*[@id=\"answer-2-0\"]"));
		answer3.click();
		scrollBy("400");
		//第４問
		WebElement answer4 = webDriver.findElement(By.xpath("//*[@id=\"answer-3-0\"]"));
		answer4.click();
		scrollBy("400");
		//第５問
		WebElement answer5 = webDriver.findElement(By.xpath("//*[@id=\"answer-4-1\"]"));
		answer5.click();
		scrollBy("400");
		//第６問
		WebElement answer6 = webDriver.findElement(By.xpath("//*[@id=\"answer-5-1\"]"));
		answer6.click();
		scrollBy("400");
		//第7問
		WebElement answer7 = webDriver.findElement(By.xpath("//*[@id=\"answer-6-0\"]"));
		answer7.click();
		scrollBy("350");
		//第8問
		WebElement answer8 = webDriver.findElement(By.xpath("//*[@id=\"answer-7-0\"]"));
		answer8.click();
		scrollBy("350");
		//第9問
		WebElement answer9 = webDriver.findElement(By.xpath("//*[@id=\"answer-8-0\"]"));
		answer9.click();
		scrollBy("350");
		//第10問
		WebElement answer10 = webDriver.findElement(By.xpath("//*[@id=\"answer-9-0\"]"));
		answer10.click();
		scrollBy("350");
		//第11門
		WebElement answer11 = webDriver.findElement(By.xpath("//*[@id=\"answer-10-0\"]"));
		answer11.click();
		scrollBy("350");
		//第12問
		WebElement answer12 = webDriver.findElement(By.xpath("//*[@id=\"answer-11-0\"]"));
		answer12.click();
		scrollBy("1000");

		//「確認画面へ進む」ボタンを押下
		WebElement gonnaConfirmationBtn = webDriver
				.findElement(By.xpath("//*[@id=\"examQuestionForm\"]/div[13]/fieldset/input"));
		gonnaConfirmationBtn.click();

		getEvidence(new Object() {
		});

		//試験回答確認画面に遷移しているかどうか
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/answerCheck");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("10000");

		getEvidence(new Object() {
		}, "1");

		//「回答を送信する」ボタンを押下
		final WebElement answer = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/div/div[13]/fieldset/form[2]/button"));
		answer.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//画面遷移待ち
		Thread.sleep(5000);

		//試験結果画面に遷移できたかどうか
		final boolean TestResults = webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/h2/small"))
				.getText().contains("あなたのスコア");
		assertTrue(TestResults);

		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("10000");

		getEvidence(new Object() {
		}, "1");

		//戻る」ボタンを押下
		final WebElement back = webDriver.findElement(
				By.xpath("/html/body/div[1]/div/div[1]/div/div/div[13]/fieldset/form/input[1]"));
		back.click();

		getEvidence(new Object() {
		}, "2-1");

		//指定位置までスクロール
		scrollBy("500");

		//当該試験の結果が反映されているかどうか
		final String TestResultCheck = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/table[2]/tbody/tr[5]/td[2]"))
				.getText();
		assertEquals("50.0点", TestResultCheck);

		//エビデンス用待ち
		Thread.sleep(5000);

		getEvidence(new Object() {
		}, "2-2");
	}

}
