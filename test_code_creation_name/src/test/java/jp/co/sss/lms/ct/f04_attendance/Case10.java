package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

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
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト02 管理者アカウントでログイン")
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

	@SuppressWarnings("static-access")
	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから、勤怠管理画面に遷移する。")
	void test03() {
		// TODO ここに追加
		//「勤怠」リンクを押下
		final WebElement Attendance = webDriver
				.findElement(
						By.cssSelector("#nav-content > ul:nth-child(1) > li:nth-child(3) > a"));
		Attendance.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//勤怠管理画面に遷移できてるかどうか
		final String AttendanceManagement = webDriver
				.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/detail", AttendanceManagement);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し、出勤時間を登録する。")
	void test04() {
		// TODO ここに追加
		//「出勤」ボタンを押下
		final WebElement ClockInButton = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div[2]/form/input[1]"));
		ClockInButton.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//出勤時間が反映されているかどうか
		final WebElement ClockInCheck = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/table/tbody/tr[2]/td[3]"));
		final boolean userDisplayed = ClockInCheck.isDisplayed();
		assertTrue(userDisplayed);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し、退勤時間を登録する。")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//「退勤」ボタンを押下
		final WebElement ClockOutButton = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[2]/form/input[2]"));
		ClockOutButton.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//退勤時間が反映されているかどうか
		final WebElement ClockInCheck = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/table/tbody/tr[2]/td[4]"));
		final boolean userDisplayed = ClockInCheck.isDisplayed();
		assertTrue(userDisplayed);

		getEvidence(new Object() {
		});

	}

}
