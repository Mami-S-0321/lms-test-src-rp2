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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// TODO ここに追加
		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver
				.findElement(
						By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//勤怠情報直接変更画面に遷移できてるかどうか
		final String AttendanceManagementDirectEditing = webDriver
				.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", AttendanceManagementDirectEditing);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		//変更できていない日程の定時ボタンを押下
		final WebElement Fixes = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > table > tbody > tr:nth-child(8) > td.w60 > button"));
		Fixes.click();

		//指定位置までスクロール
		scrollBy("1000");

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//勤怠管理画面に遷移できているか
		final String UpdateComplet = webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/span"))
				.getText();
		assertEquals("勤怠情報の登録が完了しました。", UpdateComplet);

		getEvidence(new Object() {
		}, "2");
	}

}
