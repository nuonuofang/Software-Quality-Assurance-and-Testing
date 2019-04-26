import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class StudentManagement extends JFrame implements ActionListener {

	JTabbedPane dbTabPane;
	JPanel inputPanel; // ¼�����
	JPanel viewPanel; // ������
	JPanel updatePanel; // �������
	JPanel deletePanel; // ɾ�����
	JPanel findPanel;//���ң����

	JButton inputBtn; // ¼��
	JButton clearBtn1;
	StudentPanel inputInnerPanel;

	JTextArea viewArea; // ���
	JButton viewBtn;

	JTextArea findArea; 
	StudentPanel findInnerPanel; // ��������
	JLabel findInputLbl;
	JTextField findInputText;
	JButton findBtn1;
	JButton findBtn2;
	JButton findBtn3;
	
	
	StudentPanel updateInnerPanel; // �޸�����
	JLabel updateInputLbl;
	JTextField updateInputText;
	JButton updateBtn;

	StudentPanel deleteInnerPanel;//ɾ������
	JLabel inputNameLabel;
	JTextField inputNameField;
	JButton deleteBtn;

	Connection conn;
	Statement stmt;

	public StudentManagement() {
		super("ѧ��������Ϣ����ϵͳ");
		serGUIComponent();
	}

	public void serGUIComponent() {
		// TODO Auto-generated method stub
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		dbTabPane = new JTabbedPane();

		// ����������
		viewPanel = new JPanel();
		viewBtn = new JButton("���");
		viewPanel.setLayout(new BorderLayout());
		viewArea = new JTextArea(6, 35);
		

		viewPanel.add(new JScrollPane(viewArea), BorderLayout.CENTER);
		viewPanel.add(viewBtn, BorderLayout.SOUTH);
		viewBtn.addActionListener(this);
		dbTabPane.addTab("�������", viewPanel);

		// ����������
				findPanel = new JPanel();
				findPanel.setLayout(new BorderLayout());
				findArea = new JTextArea(6, 35);
				
				findInputLbl = new JLabel("����������");//����һ����ǩ
				findInputText = new JTextField(10);//����һ�������
				findInputText.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// ������������ʾ�������
						viewARecord(findInputText.getText(), findInnerPanel);
						//findInputText.setText("");//�����
					}
				});
				
				
				findBtn1 = new JButton("����");
				findBtn2 = new JButton("�޸�");
				findBtn3 = new JButton("ɾ��");
				findBtn1.addActionListener(this);	
				
				findPanel.add(new JScrollPane(findArea), BorderLayout.CENTER);
				findPanel.add(findBtn1, BorderLayout.NORTH);
				findPanel.add(findBtn2, BorderLayout.NORTH);
				findPanel.add(findBtn3, BorderLayout.NORTH);
				
				findBtn1.addActionListener(this);
				
		
				findInnerPanel = new StudentPanel();
				
				//findBtn1 = new JButton("����");
			
				findPanel = new JPanel();

				findPanel.add(findInputLbl);
				findPanel.add(findInputText);
				findPanel.add(findBtn1);
				findPanel.add(findBtn2);
				findPanel.add(findBtn3);

				findPanel.add(findInnerPanel);
				
				findPanel.add(findInnerPanel);
				dbTabPane.addTab("��������", findPanel);
				dbTabPane.add("��������", findPanel);
				
				
		// ����¼�����
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputBtn = new JButton("¼��");
		clearBtn1 = new JButton("���");
		/* ʹ��this���󣬿��Բ��������ڲ����ֱ���ڱ�����ʵ�ֽӿڸ��ǽӿڵķ����� */
		inputBtn.addActionListener(this);
		clearBtn1.addActionListener(this);
		inputInnerPanel = new StudentPanel();
		inputPanel.add(inputInnerPanel);
		inputPanel.add(inputBtn);
		inputPanel.add(clearBtn1);
		dbTabPane.add("¼������", inputPanel);

		// ����������
		// findBtn1=new JButton("����������");

		// ����������
		updateInnerPanel = new StudentPanel();
		updateInputLbl = new JLabel("��֪������");//����һ����ǩ
		updateInputText = new JTextField(10);//����һ�������
		updateInputText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������������ʾ�������
				viewARecord(updateInputText.getText(), updateInnerPanel);
				updateInputText.setText("");
			}
		});
		updateBtn = new JButton("�޸�");
		updateBtn.addActionListener(this);
		updatePanel = new JPanel();

		updatePanel.add(updateInputLbl);
		updatePanel.add(updateInputText);
		updatePanel.add(updateBtn);

		updatePanel.add(updateInnerPanel);
		dbTabPane.add("�޸�����", updatePanel);

		// ����ɾ�����
		deleteInnerPanel = new StudentPanel();
		inputNameLabel = new JLabel("��֪������");
		inputNameField = new JTextField(10);
		inputNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ɾ��������ʾ�������
				viewARecord(inputNameField.getText(), deleteInnerPanel);
				inputNameField.setText("");
			}
		});
		deleteBtn = new JButton("ɾ��");
		deleteBtn.addActionListener(this);
		deletePanel = new JPanel();
		deletePanel.add(deleteInnerPanel);
		deletePanel.add(inputNameLabel);
		deletePanel.add(inputNameField);
		deletePanel.add(deleteBtn);
		dbTabPane.add("ɾ������", deletePanel);

		c.add(BorderLayout.NORTH, dbTabPane);

	}

	public void connection() { // �����������ݿ�
		try {
			Class.forName("com.mysql.jdbc.Driver"); // JDBC-ODBC�Ž���
			// System.out.println("�����Ѽ���");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_studentmanage", "root",
					"root");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
			e2.getSQLState();
			e2.getMessage();
		}
	}

	public void close() { // �ر�ִ���������ݿ�
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e2) {
			System.out.println("���������ر�");
		}
	}

	public void inputRecords() { // ¼��ѧ������
		String id = inputInnerPanel.getId();
		String name = inputInnerPanel.getName();
		String gender = inputInnerPanel.getGender();
		String birDate = inputInnerPanel.getBirDate();
		String address = inputInnerPanel.getAddress();
		String tel = inputInnerPanel.getTel();

		try {
			connection();
			String InsSQL;
			InsSQL = "INSERT INTO student(ѧ��,����,�Ա�,��������,��ͥסַ,��ϵ�绰)" + "VALUES("
					+ "'" + id + "'," + "'" + name + "'," + "'" + gender + "',"
					+ "'" + birDate + "'," + "'" + address + "'," + "'" + tel
					+ "')";
			stmt.execute(InsSQL);
			JOptionPane.showMessageDialog(null, "����ɹ�");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	
	//���ѧ������
	public void viewARecord(String name, StudentPanel p) {
		try {
			connection();
			String viewSQL = "SELECT * From student where ����='" + name + "'";
			ResultSet rs = stmt.executeQuery(viewSQL);
			if (rs.next()) {
				p.setName(rs.getString("����"));
				p.setId(rs.getString("ѧ��"));
				p.setGender(rs.getString("�Ա�"));
				p.setBirDate(rs.getString("��������"));
				p.setAddress(rs.getString("��ͥסַ"));
				p.setTel(rs.getString("��ϵ�绰"));
			}
		} catch (SQLException e) {
			System.out.println("���ѧ����¼ʧ��");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void viewRecords() {
		try {
			viewArea.setText("");
			String viewString = "";
			connection();
			ResultSet rs = stmt
					.executeQuery("SELECT * From student order by ѧ��");// ��ѧ�ŵ�������
			ResultSetMetaData rsMeta = rs.getMetaData();
			int nums = rsMeta.getColumnCount();
			// ����ֶ�����
			for (int i = 1; i <= nums; i++) {
				viewString += rsMeta.getColumnName(i) + "\t";
			}
			viewString += "\n";

			// ������ݱ�student�ļ�¼
			while (rs.next()) {
				for (int i = 1; i <= nums; i++) {
					viewString += rs.getString(i) + "\t";
				}
				viewString += "\n";
				viewArea.setText(viewString);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("���ѧ����¼ʧ��");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	
	public void findRecords(String name) {
		try {
			findArea.setText("");
			String findString = "";
			connection();
			ResultSet rs = stmt
					.executeQuery("SELECT * From student where ����='" + name +"'");// ��ѧ�ŵ�������
			ResultSetMetaData rsMeta = rs.getMetaData();
			int nums = rsMeta.getColumnCount();
			// ����ֶ�����
			for (int i = 1; i <= nums; i++) {
				findString += rsMeta.getColumnName(i) + "\t";
			}
			findString += "\n";

			// ������ݱ�student�ļ�¼
			while (rs.next()) {
				for (int i = 1; i <= nums; i++) {
					findString += rs.getString(i) + "\t";
				}
				findString += "\n";
				findArea.setText(findString);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("����ѧ����¼ʧ��");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void updateRecord(String name) { // �޸�����Ϊnameѧ���ļ�¼
		String updateSQL = "update student set ���� =?," + "�Ա�=?," + "��������=?,"
				+ "��ͥסַ=?," + "��ϵ�绰=?" + "" + "where ����='" + name + "'";
		PreparedStatement stmt;

		try {
			connection();
			stmt = conn.prepareStatement(updateSQL);
			stmt.setString(1, updateInnerPanel.getName());
			stmt.setString(2, updateInnerPanel.getGender());
			stmt.setString(3, updateInnerPanel.getBirDate());
			stmt.setString(4, updateInnerPanel.getAddress());
			stmt.setString(5, updateInnerPanel.getTel());
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "�޸����ݳɹ�");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	//ɾ������
	public void deleteRecord(String name) {
		String delStr = "delete from student where ����='" + name + "'";
		try {
			connection();
			
				stmt.execute(delStr);
				boolean flag=stmt.execute(delStr);
				if(flag==true){
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
				}
				else{
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StudentManagement app = new StudentManagement();
		app.setSize(500, 260);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == inputBtn) {
			inputRecords();
		} else if (e.getSource() == viewBtn) {
			viewRecords();
		} else if (e.getSource() == updateBtn) {
			updateRecord(updateInnerPanel.getName());
		} else if (e.getSource() == deleteBtn) {
			deleteRecord(deleteInnerPanel.getName());
			deleteInnerPanel.clearContent();
		} else if (e.getSource() == clearBtn1) {
			inputInnerPanel.clearContent();
		}else if(e.getSource() == findBtn1){
			findRecords(findInnerPanel.getName());
		}
	}

}
