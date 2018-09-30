@SuppressWarnings("all")
public class IDNMaker extends JFrame implements ActionListener {
	
	private JPanel jp = null;
	private JButton jb1 = null;
	private JButton jb2 = null;
	private JButton jb3 = null;
	private JComboBox jcombo1 = null;
	private JComboBox jcombo2 = null;
	private JTextField jtf1 = null;
	
	private String HSN[] = {"台北市" , "台中市" , "基隆市" , "台南市" , "高雄市" , "新北市" , "宜蘭縣" , "桃園市" , "嘉義市" , "新竹縣"
			, "苗栗縣" , "臺中縣", "南投縣" , "彰化縣" , "新竹市" , "雲林縣" , "嘉義縣" , "臺南縣" , "高雄縣" , "	屏東縣" , "花蓮縣" , "台東縣"
			, "金門縣" , "澎湖縣" , "陽明山" , "連江縣"};
	
	private int[] locationNumber = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 
			24, 25, 26, 27, 28, 29, 32, 30, 31, 33 };
	private char[] locationChar = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public IDNMaker() {
		this.setBounds(20, 20, 300, 200);
		this.setTitle("身分證產生器");
		this.getContentPane().setLayout(null);
		jcombo1 = new JComboBox();
		jcombo1.setModel(new DefaultComboBoxModel(HSN));
		
		jcombo2 = new JComboBox();
		jcombo2.setModel(new DefaultComboBoxModel(new String[]{"男" , "女"}));
		
		jtf1 = new JTextField();
		jtf1.setColumns(20);
	
		jb1 = new JButton("產生");
		jb1.setSize(30,30);
		jb1.addActionListener(this);
		
		jb2 = new JButton("驗證");
		jb2.setSize(30,30);
		jb2.addActionListener(this);
		
		jb3 = new JButton("關閉");
		jb3.setSize(30,30);
		jb3.addActionListener(this);
		
		jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.setBounds(0, 0, 250, 200);
		jp.add(jcombo1);
		jp.add(jcombo2);
		jp.add(jtf1);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.setVisible(true);
		
		this.getContentPane().add(jp);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ("產生".equals(e.getActionCommand())) {
			randomIDN();
		} else if ("驗證".equals(e.getActionCommand())) {
			if ("".equals(jtf1.getText())) {
				JOptionPane.showMessageDialog(null, "請輸入身分證字號", "訊息" , 0);
				return;
			}
			
			if (jtf1.getText().matches("[A-Z][12]\\d{8}")) {
				String idn = jtf1.getText();
				if(checkID(idn)) {
					JOptionPane.showMessageDialog(null, "有效的身分證字號", "訊息" , 1);
				} else {
					JOptionPane.showMessageDialog(null, "無效的身分證字號", "訊息" , JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "身分證字號格式不正確", "訊息" , JOptionPane.WARNING_MESSAGE);
				return;
			}
		} else if ("關閉".equals(e.getActionCommand())) {
			System.exit(0);
		}
	}
	private void randomIDN() {
		char idnEng = locationChar[jcombo1.getSelectedIndex()];
		int sex = jcombo2.getSelectedIndex() + 1;
		int num = (int)((Math.random() * 89999999) + 10000000);
		
		String indNum = String.valueOf(idnEng) + sex + "" + num;
		if (checkID(indNum)) {
			jtf1.setText(indNum);
		} else {
			randomIDN();
		}
	}
	
	private boolean checkID(String idn) {
		int checkNum[] = {8, 7, 6, 5, 4, 3, 2, 1 ,1};
		boolean checkLocation = false;
		String idnEng = idn.substring(0,1);
		
		for (int i = 0; i < locationChar.length; i++) {
			if(idnEng.equals(String.valueOf(locationChar[i]))) {
				idnEng = String.valueOf(locationNumber[i]);
				String idnNum = idn.substring(1, 10);
				int sum = Integer.parseInt(idnEng.substring(0,1)) + (Integer.parseInt(idnEng.substring(1)) * 9);
				
				for(int j = 0; j < idnNum.length(); j++) {
					sum += (idnNum.charAt(j) - '0') * checkNum[j];
				}
				if(sum % 10 == 0) {
					checkLocation = true;
				}
			}
		}
		return checkLocation;
	}
	
	public static void main(String[] args) {
		IDNMaker idn = new IDNMaker();
	}
}
