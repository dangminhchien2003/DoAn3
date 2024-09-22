package Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import DTO.BienBao;
import DTO.LoaiBienBao;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "AppOnThiGPLX";
    static final int dbVersion = 1;
    private SQLiteDatabase db;
    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableManagers = "create table Managers (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "password TEXT NOT NULL )";
        db.execSQL(createTableManagers);

        String createTableLoaiBienbao = "create table LoaiBienBao (" +
                "id TEXT PRIMARY KEY," +
                "tenloai TEXT NOT NULL," +
                "mota TEXT NOT NULL," +
                "anh TEXT NOT NULL)";
        db.execSQL(createTableLoaiBienbao);

        String createTableBienbao = "CREATE TABLE BienBao (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_loai TEXT NOT NULL," +
                "tenbien TEXT NOT NULL," +
                "sobien TEXT NOT NULL," +
                "mota TEXT NOT NULL," +
                "anh TEXT NOT NULL," +
                "FOREIGN KEY (id_loai) REFERENCES LoaiBienBao(id)" +
                ");";
        db.execSQL(createTableBienbao);

        String createTableLoaiCauhoi = "create table Loaicauhoi (" +
                "id TEXT PRIMARY KEY," +
                "tenloai TEXT NOT NULL," +
                "mota TEXT NOT NULL," +
                "anh TEXT NOT NULL)";
        db.execSQL(createTableLoaiCauhoi);

        String createTableDeThi = "CREATE TABLE DeThi (" +
                "id_de TEXT PRIMARY KEY," +
                "ten_de TEXT NOT NULL)";
        db.execSQL(createTableDeThi);

        String createTableCauHoi = "CREATE TABLE CauHoi (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_loaiCH TEXT NOT NULL," +
                "Cau TEXT NOT NULL," +
                "noi_dung TEXT NOT NULL," +
                "dap_an_a TEXT NOT NULL," +
                "dap_an_b TEXT NOT NULL," +
                "dap_an_c TEXT NOT NULL," +
                "dap_an_d TEXT NOT NULL," +
                "dap_an_dung TEXT NOT NULL," +
                "giai_thich TEXT NOT NULL," +
                "id_de TEXT NOT NULL," +
                "anh TEXT NOT NULL," +
                "FOREIGN KEY (id_loaiCH) REFERENCES Loaicauhoi(id)," +
                "FOREIGN KEY (id_de) REFERENCES DeThi(id_de)" +
                ")";
        db.execSQL(createTableCauHoi);

        // Add data to DeThi table
        db.execSQL("INSERT INTO DeThi (id_de, ten_de) VALUES " +
                "('D1', 'Đề 1')");
        db.execSQL("INSERT INTO DeThi (id_de, ten_de) VALUES " +
                "('D2', 'Đề 2')");
        db.execSQL("INSERT INTO DeThi (id_de, ten_de) VALUES " +
                "('D3', 'Đề 3')");

        // Thêm dữ liệu vào bảng LoaiCauHoi
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L1', 'Câu hỏi điểm liệt', '20 câu, sai một trong 20 câu này sẽ trượt phần thi lý thuyết', 'cauhoidiemliet')");
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L2', 'Khái niệm và quy tắc', '83 câu (có 18 câu điểm liệt)', 'khainiemvaquytac')");
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L3', 'Văn hóa và đạo đức lái xe', '5 câu', 'vanhoavadaoduclaixe')");
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L4', 'Kỹ thuật lái xe', '12 câu(có 2 câu điểm liệt)', 'kythuatlaixe')");
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L5', 'Biển báo đường bộ', '65 câu', 'bienbaoduongbo')");
        db.execSQL("INSERT INTO Loaicauhoi (id, tenloai, mota, anh) VALUES " +
                "('L6', 'Sa hình', '35 câu', 'sahinh35cau')");

        // Thêm dữ liệu bảng Cauhoi
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L1','Câu 15', 'Cuộc đua xe chỉ được thực hiện khi nào'," +
                " 'A. Diễn ra trên đường phố không có người qua lại'," +
                " 'B. Được người dân ủng hộ'," +
                " 'C. Được cơ quan có thẩm quyền cấp phép'," +
                " 'D. Cả A,B,C'," +
                " 'C'," +
                "'Cuộc đua xe cần cấp phép','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L1','Câu 16', 'Người điều khiển phương tiện giao thông đường bộ mà trong cơ thể có chất ma tuý có bị nghiêm cấm hay không?'," +
                " 'A. Bị nghiêm cấm.'," +
                " 'B. Không bị nghiêm cấm.'," +
                " 'C. Không bị nghiêm cấm, nếu có chất ma tuý ở mức nhẹ, có thể điều khiển phương tiện tham gia giao thông.'," +
                " 'D. Đáp án D'," +
                " 'A'," +
                "'Có ma túy bị nghiêm cấm','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L1','Câu 17', 'Sử dụng rượu, bia khi lái xe, nếu bị phát hiện thì bị xử lý như thế nào?'," +
                " 'A. Chỉ bị nhắc nhỏ.'," +
                " 'B. Bị xử phạt hành chính hoặc có thể bị xử lý hình sự tùy theo mức độ vi phạm.'," +
                " 'C. Không bị xử lý hình sự.'," +
                " 'D. Đáp án D'," +
                " 'B'," +
                "'Sử dụng rượu bia khi lái xe bị phạt hành chính hoặc xử lý hình sự','D2', '')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L1','Câu 18', 'Theo Luật phòng chống tác hại của rượu, bia, đối tượng nào dưới đây bị cấm sử dụng rượu, bia khi tham gia giao thông?'," +
                " 'A. Người điều khiển: Xe ô tô, xe mô tô, xe đạp, xe gắn máy.'," +
                " 'B. Người ngồi phía sau người điều khiển xe cơ giới.'," +
                " 'C. Người đi bộ.'," +
                " 'D. Cả ý 1 và ý 2.'," +
                " 'A'," +
                " 'Người điều khiển bị cấm sử dụng rượu, bia khi tham gia giao thông','D2', '')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L1','Câu 19', 'Hành vi điều khiển xe cơ giới chạy quá tốc độ quy định, giành đường, vượt ẩu có bị nghiêm cấm hay không?'," +
                " 'A. Bị nghiêm cấm tùy từng trường hợp.'," +
                " 'B. Không bị nghiêm cấm.'," +
                " 'C. Bị nghiêm cấm.'," +
                " 'D. Cả ý 1 và ý 2.'," +
                " 'C'," +
                " '','D3', '')");

        //câu hỏi loại 2
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L2','Câu 1', 'Phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại là gì?'," +
                " 'A. Phần mặt đường và lề đường.'," +
                " 'B. Phần đường xe chạy.'," +
                " 'C. Phần đường xe cơ giới.'," +
                " 'D. Tất cả đáp án trên'," +
                " 'B'," +
                "'Phần đường xe chạy là phần của đường bộ đưọc sử dụng cho phương tiện giao thông qua lại'," +
                "'D1', '')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L2','Câu 2', '\"Làn đường\" là gì?'," +
                " 'A. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, sử dụng cho xe chạy.'," +
                " 'B. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn.'," +
                " 'C. Là đường cho xe ô tô chạy, dừng, đỗ an toàn.'," +
                " 'D. Không có đáp án đúng'," +
                " 'B'," +
                "'Làn đường có bề rộng đủ cho xe chạy an toàn','D2', '')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L2','Câu 3', 'Trong các khái niệm dưới đây, “dải phân cách\" được hiểu như thế nào là đúng?'," +
                " 'A. Là bộ phận của đường để ngăn cách không cho các loại\n" +
                "xe vào những nơi không được phép.'," +
                " 'B. Là bộ phận của đường để phân tách phần đường xe chạy và hành lang an toàn giao thông.'," +
                " 'C. Là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ.'," +
                " 'D. Không có đáp án đúng'," +
                " 'C'," +
                "'Dải phân cách phân chia mặt đường thành hai chiều xe chạy riêng biệt','D2','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L2','Câu 4', '\"Dải phân cách\" trên đường bộ gồm những loại nào?'," +
                " 'A. Dải phân cách gồm loại cố định và loại di động.'," +
                " 'B. Dải phân cách gồm tường chống ồn, hộ lan cứng và hộ lan mềm.'," +
                " 'C. Dải phân cách gồm giá long môn và biển báo hiệu đường bộ.'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                "'Dải phân cách gồm cố định và di động','D3', '')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L2','Câu 5', 'Người lái xe được hiểu như thế nào trong các khái niệm dưới đây?'," +
                " 'A. Là người điều khiển xe cơ giới.'," +
                " 'B. Là người điều khiển xe thô sơ.'," +
                " 'C. Là người điều khiển xe có súc vật kéo.'," +
                " 'D. Tất cả đáp án trên'," +
                " 'A'," +
                "'Người lái xe là người điều khiển xe cơ giới','D3','')");

        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich, id_de, anh) VALUES " +
                "('L3','Câu 84', 'Khái niệm về văn hóa giao thông được hiểu như thế nào là đúng?'," +
                " 'A. Là sự hiểu biết và chấp hành nghiêm chỉnh pháp luật về giao thông; là ý thức trách nhiệm với cộng đồng khi tham gia giao thông.'," +
                " 'B.Là ứng xử có văn hóa, có tình yêu thương con người trong các tình huống không may xảy ra khi tham gia giao thông.'," +
                " 'C. Cả ý 1 và ý 2.'," +
                " 'D. Không có đáp án đúng'," +
                " 'C'," +
                " '','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich, id_de, anh) VALUES " +
                "('L3','Câu 85', 'Trong các hành vi dưới đây, người lái xe mô tô có văn hóa giao thông phải ứng xử như thế nào?'," +
                " 'A. Điều khiển xe đi bên phải theo chiều đi của mình; đi đúng phần đường, làn đường quy định; đội mũ bảo hiểm đạt chuẩn, cài quai đúng quy cách.'," +
                " 'B. Điều khiển xe đi trên phần đường, làn đường có ít phương tiện tham gia giao thông.'," +
                " 'C. Điều khiển xe và đội mũ bảo hiểm ở nơi có biển báo bắt buộc đội mũ bảo hiểm.'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                " '','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich, id_de, anh) VALUES " +
                "('L3','Câu 86', 'Trong các hành vi dưới đây, người lái xe ô tô, mô tô có văn hóa giao thông phải ứng xử như thế nào?'," +
                " 'A. Điều khiển xe đi bên phải theo chiều đi của mình; đi đúng phần đường, làn đường quy định; dừng, đỗ xe đúng nơi quy định; đã uống rượu, bia thì không lái xe.'," +
                " 'B. Điều khiển xe đi trên phần đường, làn đường có ít phương tiện giao thông; dừng xe, đỗ xe ở nơi thuận tiện hoặc theo yêu cầu của hành khách, của người thân.'," +
                " 'C. Dừng và đỗ xe ở nơi thuận tiện cho việc chuyên chở hành khách và giao nhận hàng hóa; sử dụng ít rượu, bia thì có thể lái xe.'," +
                " 'D. Tất cả đều đúng'," +
                " 'A'," +
                " '','D2','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich, id_de, anh) VALUES " +
                "('L3','Câu 87', 'Khi xảy ra tai nạn giao thông, có người bị thương nghiêm trọng, người lái xe và người có mặt tại hiện trường vụ tai nạn phải thực hiện các công việc gì dưới đây?'," +
                " 'A. Thực hiện sơ cứu ban đầu trong trường hợp khẩn cấp; thông báo vụ tai nạn đến cơ quan thi hành pháp luật.'," +
                " 'B. Nhanh chóng lái xe gây tai nạn hoặc đi nhờ xe khác ra khỏi hiện trường vụ tai nạn.'," +
                " 'C. Cả ý 1 và ý 2.'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                " '','D3','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich, id_de, anh) VALUES " +
                "('L3','Câu 88', 'Trên đường đang xảy ra ùn tắc những hành vi nào sau đây là thiếu văn hóa khi tham gia giao thông?'," +
                " 'A. Bấm còi liên tục thúc giục các phương tiện phía trước nhường đường.'," +
                " 'B. Đi lên vỉa hè, tận dụng mọi khoảng trống để nhanh chóng thoát khỏi nơi ùn tắc.'," +
                " 'C. Lần sang trái đường cố gắng vượt lên xe khác.'," +
                " 'D. Tất cả các ý nêu trên.'," +
                " 'D'," +
                " '','D3','')");

        //cauhoi loai 4
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L4','Câu 89', 'Khi điều khiển xe mô tô tay ga xuống đường dốc dài, độ dốc cao, người lái xe cần thực hiện các thao tác nào dưới đây để đảm bảo an toàn?'," +
                " 'A. Giữ tay ga ở mức độ phù hợp, sử dụng phanh trước và phanh sau để giảm tốc độ.'," +
                " 'B. Nhà hết tay ga, tắt động cơ, sử dụng phanh trước và phanh sau để giảm tốc độ.'," +
                " 'C. Sử dụng phanh trước để giảm tốc độ kết hợp với tắt chìa khóa điện của xe.'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                "'Xe mô tô xuống dốc dài cần sử dụng cả phanh trước và phanh sau','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L4','Câu 90', 'Khi quay đầu xe, người lái xe cần phải quan sát và thực hiện các thao tác nào để đảm bảo an toàn giao thông?'," +
                " 'A. Quan sát biển báo hiệu để biết nơi được phép quay đầu; quan sát kỹ địa hình nơi chọn để quay đầu; lựa chọn quỹ đạo quay đầu xe cho thích hợp; quay đầu xe với tốc độ thấp; thường xuyên báo tín hiệu để người, các phương tiện xung quanh được biết, nếu quay đầu xe ở nơi nguy hiểm thì đưa đầu xe về phía nguy hiểm đưa đuôi xe về phía an toàn.'," +
                " 'B. Quan sát biển báo hiệu để biết nơi được phép quay đầu; quan sát kỹ địa hình nơi chọn để quay đầu; lựa chọn quỹ đạo quay đầu xe; quay đầu xe với tốc độ tối đa; thường xuyên báo tín hiệu để người, các phương tiện xung quanh được biết; nếu quay đầu xe ở nơi nguy hiểm thì đưa đuôi xe về phía nguy hiểm và đầu xe về phía an toàn.'," +
                " 'C. Cả A và B đều đúng'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                "'Thực hiện quay đầu xe với tốc độ thấp','D1','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L4','Câu 91', 'Khi tránh nhau trên đường hẹp, người lái xe cần phải chú ý những điểm nào để đảm bảo an toàn giao thông?'," +
                " 'A. Không nên đi cổ vào đường hẹp; xe đi ở phía sườn núi nên dừng lại trước để nhường đường; khi dừng xe nhường đường phải đỗ ngay ngắn.'," +
                " 'B. Trong khi tránh nhau không nên đổi số; khi tránh nhau ban đêm, phải tắt đèn pha bật đèn cốt.'," +
                " 'C. Khi tránh nhau ban đêm, phải thường xuyên bật đèn pha tắt đèn cốt.'," +
                " 'D. Cả ý 1 và ý 2.'," +
                " 'D'," +
                "'Cả ý 1 và 2 đều đúng','D2','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L4','Câu 92', 'Khi điều khiển xe trên đường vòng người lái xe cần phải làm gì để đảm bảo an toàn?'," +
                " 'A. Quan sát cẩn thận các chướng ngại vật và báo hiệu bằng còi, đèn; giảm tốc độ tới mức cần thiết, về số thấp và thực hiện quay vòng với tốc độ phù hợp với bán kính cong của đường vòng.'," +
                " 'B. Quan sát cẩn thận các chướng ngại vật và báo hiệu bằng còi, đèn; tăng tốc để nhanh chóng qua đường vòng và giảm tốc độ sau khi qua đường vòng.'," +
                " 'C. Cả A và B đều đúng'," +
                " 'D. Không có đáp án đúng'," +
                " 'A'," +
                "'Điều khiển xe trên đường vòng cần giảm tốc độ','D2','')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L4','Câu 93', 'Để đạt được hiệu quả phanh cao nhất, người lái xe mô tô phải sử dụng các kỹ năng như thế nào dưới đây?'," +
                " 'A. Sử dụng phanh trước.'," +
                " 'B. Sử dụng phanh sau.'," +
                " 'C. Giảm hết ga; sử dụng đồng thời cả phanh sau và phanh trước.'," +
                " 'D. Không có đáp án đúng'," +
                " 'C'," +
                "'Khi phanh xe mô tô thi giảm hết ga','D3','')");

        //Cau hoi loai 5
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L5','Câu 101', 'Biền nào dưới đây xe gắn máy được phép đi vào?'," +
                " 'A. Biển 1.'," +
                " 'B. Biển 2.'," +
                " 'C. Cả hai biển.'," +
                " 'D. Không có biển nào'," +
                " 'C'," +
                "'Biển 104 cấm mô tô và biển 103a cấm ô tô không cấm xe gắn máy(không phải mô tô). Nên cả 2 biển đều cho phép xe gắn máy đi vào.'," +
                " 'D1','bienbaoduongbo1')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L5','Câu 102', 'Biển nào báo hiệu cấm xe mô tô hai bánh đi vào?'," +
                " 'A. Biển 1.'," +
                " 'B. Biển 2.'," +
                " 'C. Biển 3.'," +
                " 'D. Không có biển nào'," +
                " 'A'," +
                " '','D1','bienbaoduongbo2')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L5','Câu 103', 'Khi gặp biển nào thì xe mô tô hai bánh được đi vào?'," +
                " 'A. Không biển nào.'," +
                " 'B. Biển 1 và 2.'," +
                " 'C. Biển 2 và 3.'," +
                " 'D. Cả ba biển.'," +
                " 'C'," +
                "'','D2','bienbaoduongbo2')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L5','Câu 104', 'Biển nào cầm quay đầu xe?'," +
                " 'A. Biến 1.'," +
                " 'B. Biến 2.'," +
                " 'C. Không biển nào.'," +
                " 'D. Cả hai biển.'," +
                " 'B'," +
                "'Biển 1: P.123a cấm rẽ trái không cấm quay đầu xe; Biển 2: P124a cấm quay xe không cấm rẽ trái'," +
                "'D3', 'bienduongbo4')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L5','Câu 105', 'Biển nào cấm xe rẽ trái?'," +
                " 'A. Biến 1.'," +
                " 'B. Biến 2.'," +
                " 'C. Cả hai biển.'," +
                " 'D. Không biển nào.'," +
                " 'A'," +
                "'Biển 1: P.123a cấm rẽ trái không cấm quay đầu xe; Biển 2: P124a cấm quay xe không cấm rẽ trái'," +
                "'D3','bienduongbo4')");

        //cau hoi loai 6
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L6','Câu 166', 'Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?'," +
                " 'A. Xe tải, xe khách, xe con, mô tô.'," +
                " 'B. Xe tải, mô tô, xe khách, xe con.'," +
                " 'C. Xe khách, xe tải, xe con, mô tô.'," +
                " 'D. Mô tô, xe khách, xe tải, xe con.'," +
                " 'B'," +
                "'Thứ tự ưu tiên: Xe ưu tiên - Đường cùng cấp theo thứ tự bên phải trống- rẽ phải - đi thẳng - rẽ trái'," +
                "'D1','sahinh1')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L6','Câu 167', 'Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?'," +
                " 'A. Xe tải, xe con, mô tô.'," +
                " 'B. Xe con, xe tải, mô tô.'," +
                " 'C. Mô tô, xe con, xe tải.'," +
                " 'D. Xe con, mô tô, xe tải.'," +
                " 'C'," +
                "'Thứ tự ưu tiên: Xe ưu tiên - Đường cùng cấp theo thứ tự bên phải trống - rẽ phải - đi thẳng - rẽ trái'," +
                "'D2','sahinh2')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L6','Câu 168', 'Trường hợp này xe nào được quyền đi trước?'," +
                " 'A. Mô tô.'," +
                " 'B. Xe con.'," +
                " 'C. Cả 2 xe'," +
                " 'D. Không có xe nào'," +
                " 'B'," +
                "'Xe con được đi trước vì mô tô biển STOP trước mặt.'," +
                "'D2','sahinh3')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L6','Câu 169', 'Xe nào được quyền đi trước trong trường hợp này?'," +
                " 'A. Mô tô.'," +
                " 'B. Xe cứu thương.'," +
                " 'C. Cả 2 xe'," +
                " 'D. Không có xe nào'," +
                " 'B'," +
                "'Xe ưu tiên đi trước'," +
                "'D3','sahinh4')");
        db.execSQL("INSERT INTO CauHoi (id_loaiCH, Cau, noi_dung, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, giai_thich,id_de, anh) VALUES " +
                "('L6','Câu 170', 'Theo tín hiệu đèn, xe nào được phép đi?'," +
                " 'A. Xe con và xe khách.'," +
                " 'B. Mô tô.'," +
                " 'C. Xe con.'," +
                " 'D. Xe khách.'," +
                " 'A'," +
                "'Xe con và xe khách ở làn đường có tín hiệu đèn xanh nên được phép đi.'," +
                "'D3','sahinh5')");

        // Thêm dữ liệu vào bảng LoaiBienBao
        db.execSQL("INSERT INTO LoaiBienBao (id, tenloai, mota, anh) VALUES " +
                "('1', 'Biển báo cấm', 'Biển có dạng hình tròn, viền đỏ', 'biencam')");
        db.execSQL("INSERT INTO LoaiBienBao (id, tenloai, mota, anh) VALUES " +
                "('2', 'Biển báo nguy hiểm', 'Biển có dạng hình tam giác, viền đỏ', 'biennguyhiem')");
        db.execSQL("INSERT INTO LoaiBienBao (id, tenloai, mota, anh) VALUES " +
                "('3', 'Biển chỉ dẫn', 'Biển có dạng hình chữ nhật đứng, không có viền, nền màu xanh', 'bienchidan')");
        db.execSQL("INSERT INTO LoaiBienBao (id, tenloai, mota, anh) VALUES " +
                "('4', 'Biển hiệu lệnh', 'Biển có dạng hình tròn, không có viền, nền màu xanh', 'bienhieulenh')");
        db.execSQL("INSERT INTO LoaiBienBao (id, tenloai, mota, anh) VALUES " +
                "('5', 'Biển phụ', 'Biển có dạng hình chữ nhật ngang, vị trí đặt ngay sát dưới biển chính (nếu có)', 'bienphu')");


        // Thêm dữ liệu vào bảng BienBao
        //Biển báo cấm
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('1', 'Đường cấm','Biển 101', 'Báo đường cấm tất cả các loại xe (xe cơ giới và xe thô sơ) đi lại cả 2" +
                "hướng, trừ các xe được ưu tiên theo luật lệ Nhà nước quy định.', 'biencam')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('1', 'Cấm đi ngược chiều','Biển 102', '(Báo đường cấm tất cả các loại xe (xe cơ giới và xe thô sơ)" +
                " đi vào làn đường bị cấm theo hướng đặt biển, ngoại trừ các xe ưu tiên theo quy định.', 'camdinguocchieu')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('1', 'Cấm ô tô','Biển 103', 'Báo đường cấm tất cả các loại xe cơ giới kể cả mô tô 3 bánh co thùng đi qua," +
                "trừ mô tô 2 bánh, xe gắn máy và các xe ưu tiên theo luật lệ Nhà nước quy định', 'camoto')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('1', 'Cấm mô tô','Biển 104', 'Báo đường cấm tất cả các loại xe mô tô đi qua,trừ các xe ưu tiên theo luật " +
                "lệ Nhà nước quy định', 'cammoto')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('1', 'Cấm ô tô và mô tô','Biển 105', 'Báo đường cấm tất cả các loại xe cơ giới và mô tô đi qua, trừ" +
                "các xe ưu tiên theo luật lệ Nhà nước quy định', 'cammotovaoto')");

        //Biển báo nguy hiểm
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('2', 'Chỗ ngoặt nguy hiểm vòng bên trái','Biển 201a', 'Báo trước sắp đến một chỗ nguy hiểm vòng bên trái.'" +
                ", 'chongoatnguyhiemvongbentrai')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('2', 'Chỗ ngoặt nguy hiểm vòng bên phải','Biển 201b', 'Báo trước sắp đến một chỗ nguy hiểm vòng bên phải.'" +
                ", 'chongoatnguyhiemvongbenphai')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('2', 'Nhiểu chỗ ngoặt nguy hiểm liên tiếp','Biển 202', 'Biển báo hiệu sắp đến đoạn đường ngoặt liên tiếp (có từ " +
                "ba đoạn cong ngược chiều nhau) nguy hiểm lái xe cần giảm tốc độ.', 'biennguyhiem')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('2', 'Đường bị hẹp cả hai bên','Biển 203a', 'Báo trước sắp đến một đoạn đường bị hẹp đột ngột cả hai bên'," +
                " 'hephaiben')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('2', 'Đường bị hẹp về bên trái','Biển 203b', 'Báo trước sắp đến một đoạn đường bị hẹp đột ngột phía bên trái'," +
                " 'hepbentrai')");

        //Biển chỉ dẫn
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('3', 'Bắt đầu đường ưu tiên','Biển 401', 'Trên đoạn đường này, phương tiện được quyền"+
                "ưu tiên đi qua nơi giao nhau không có điều khiển giao thông. Phương tiện trên đường khác " +
                "nhập vào hay cắt ngang qua phải nhường đường (trừ các loại xe được quyền ưu tiên theo Luật " +
                "Giao thông đường bộ). \nNếu ở chỗ giao nhau có điều khiển giao thông thì nguyên tắc chạy xe" +
                " ưu tiên hết tác dụng (trừ các xe được quyền ưu tiên theo Luật). Phía dưới biển số 401 phải" +
                " đặt biển phụ 506a \"Hướng đường ưu tiên\", nếu ở ngã ba, ngã tư đường ưu tiên thay đổi hướng.'" +
                ", 'batdauduonguutien')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('3', 'Hết đoạn đường ưu tiên','Biển 402', 'Đến hết đoan đường quy định là ưu tiên phải đặt " +
                "biển 402 \"Hết đoạn đường ưu tiên\". Trên đoạn đường tiếp theo, các xe cộ đi đúng với tốc độ " +
                "quy định trong Luật Giao thông đường bộ, qua nơi giao nhau ưu tiên bên phải.'" +
                ", 'hetduonguutien')");

        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('3', 'Đường dành cho ô tô','Biển 403a', 'Để chỉ dẫn bắt đầu đường dành cho các loại ô tô đi lại," +
                " các loại phương tiện giao thông khác không được phép đi vào đoạn đường có đặt biển này.'" +
                ", 'duongdanhchooto')");

        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('3', 'Hết đường dành cho ô tô','Biển 404a', 'Đến hết đoạn đường dành cho ô tô đi lại phải " +
                "đặt biển số 404a \"Hết đường dành cho ô tô\".'" +
                ", 'hetduongdanhchoooto')");

        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('3', 'Đường cụt','Biển 405a', 'Đến hết đoạn đường dành cho ô tô đi lại phải đặt biển số 404a" +
                " \"Hết đường dành cho ô tô\".', 'duongcut')");


        //Biển hiệu lệnh
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('4', 'Biển báo hiệu các xe chỉ được đi thẳng','Biển 301a', 'Khi đặt biển ở trước ngã ba," +
                " ngã tư thì hiệu lực tác dụng của biển là ở phạm vi khu vực ngã ba, ngã tư, tức là cấm xe rẽ" +
                " ở hướng tay phải và tay trái.\n" +
                "Nếu đặt biển ở sau ngã ba, ngã tư (bắt đầu vào đoạn đường phố) thì hiệu lực tác dụng của biển" +
                " kể từ chỗ đặt biển đến ngã ba, ngã tư tiếp theo. Trường hợp này cấm rẽ trái và quay đầu trong" +
                " vùng tác dụng của biển. Chỉ cho phép rẽ phải vào cổng nhà hoặc ngõ phố có đoạn đường từ ngã ba," +
                " ngã tư đặt biển đến ngã ba ngã tư tiếp theo.'" +
                ", 'bien301a')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('4', 'Biển báo hiệu các xe chỉ được rẽ phải','Biển 301b', 'Biển đặt ở sau ngã ba, ngã tư bắt buộc" +
                " người lái xe chỉ được phép rẽ phải ở phạm vi ngã ba, ngã tư trước mặt biển.'" +
                ", 'bien301b')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('4', 'Biển báo hiệu các xe chỉ được rẽ trái','Biển 301c', 'Biển đặt ở sau ngã ba, ngã tư bắt buộc người" +
                " lái xe chỉ được phép rẽ trái ở phạm vi ngã ba, ngã tư trước mặt biển.'" +
                ", 'bien301c')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('4', 'Biển báo hiệu các xe chỉ được rẽ phải','Biển 301d', 'Biển đặt ở trước ngã ba, ngã tư và bắt buộc" +
                " người lái xe chỉ được phép rẽ phải ở phạm vi ngã ba, ngã tư đằng sau mặt biển.'" +
                ", 'bien301d')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('4', 'Biển báo hiệu các xe chỉ được rẽ trái','Biển 301e', 'Biển đặt ở trước ngã ba, ngã tư và bắt buộc " +
                "người lái xe chỉ được phép rẽ trái ở phạm vi ngã ba, ngã tư đằng sau mặt biển.'" +
                ", 'bien301e')");

        //Biển phụ(9)
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Phạm vi tác dụng của biển','Biển 501', 'Để thông báo chiều dài đoạn đường nguy" +
                " hiểm hoặc cấm hoặc hiệu lệnh hoặc hạn chế bên dưới một số biển báo chính)\nChiều dài" +
                " đoạn nguy hiểm hoặc cấm hoặc hạn chế ghi theo đơn vị mét (m) và lấy chẵn đến hàng chục mét.'" +
                ", 'phamvitacdungcuabien')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Khoảng cách đến dối tượng báo hiệu','Biển 502', 'Đặt bên dưới các loại biển báo nguy" +
                " hiểm, biển báo cấm, biển hiệu lệnh và chỉ dẫn để thông báo khoảng cách thực tế từ vị trí" +
                " đặt biển đến đối tượng báo hiệu ở phía trước. Con số trên biển ghi theo đơn vị mét (m) và lấy chẵn đến hàng chục mét.'" +
                ", 'khoangcachdendoituongbaohieu')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Hướng biển báo có hiệu lực','Biển 503a', 'Đặt bên dưới các loại biển báo nguy" +
                " hiểm, biển báo cấm, biển hiệu lệnh và chỉ dẫn để thông báo khoảng cách thực tế từ vị trí" +
                " đặt biển đến đối tượng báo hiệu ở phía trước theo hướng trái hoặc phải hoặc cả hai hướng trái và phải'" +
                ", 'huongbienbaocohieuluc1')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Hướng biển báo có hiệu lực','Biển 503b', 'Đặt bên dưới các loại biển báo nguy" +
                " hiểm, biển báo cấm, biển hiệu lệnh và chỉ dẫn để thông báo khoảng cách thực tế từ vị trí" +
                " đặt biển đến đối tượng báo hiệu ở phía trước theo hướng trước hoặc sau hoặc cả hai hướng trước và sau'" +
                ", 'huongbienbaocohieuluc2')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Chỉ làn xe thi hành biển báo','Biển 504', 'Mũi tên chỉ làn đường áp dụng cho biển báo chính'" +
                ", 'chilanxethihanh')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Loại xe thi hành biển báo','Biển 505', 'Biển thông báo biển chính chỉ áp dụng đối với xe tải '" +
                ", 'loaixethihanhbienbao')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Hướng đường ưu tiên','Biển 506', 'Đặt biển dưới biển báo hiệu đường ưu tiên giữa ngã tư để xác định hướng" +
                "của đường ưu tiên.'" +
                ", 'bienphu')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Hướng rẽ','Biển 507', 'Biển báo trước cho người lái xe biết gần chỗ rẽ nguy hiểm và để chỉ hướng rẽ.'" +
                ", 'huongre')");
        db.execSQL("INSERT INTO BienBao (id_loai, tenbien,sobien, mota, anh) VALUES " +
                "('5', 'Thuyết minh biển chính','Biển 509', 'Dùng để thuyết minh rõ thêm các kiểu biển mới hoặc có kí hiệu không rõ.\n" +
                "Biển được đặt kèm theo với biển cần thuyết minh'" +
                ", 'thuyetminhbienchinh')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Managers");
        db.execSQL("DROP TABLE IF EXISTS LoaiBienBao");
        db.execSQL("DROP TABLE IF EXISTS BienBao");
        db.execSQL("DROP TABLE IF EXISTS CauHoi");
        db.execSQL("DROP TABLE IF EXISTS Loaicauhoi");
        db.execSQL("DROP TABLE IF EXISTS DeThi");

        onCreate(db);
    }

}
