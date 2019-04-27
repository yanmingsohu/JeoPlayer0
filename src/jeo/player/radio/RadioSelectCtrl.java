// CatfoOD 2009-5-11 ����05:39:36

package jeo.player.radio;


class RadioSelectCtrl {
	private RadioSelect rs;
	
	RadioSelectCtrl(RadioSelect r) {
		rs = r;
	}
	
	public void updata() {
		Mms[] mms = AutoDownRadioList.getNetMms();
		if (mms!=null) {
			for (int i=0; i<mms.length; ++i) {
				add(mms[i]);
			}
		}
		rs.setInfoText("������ɣ���ȷ��");
	}
	
	public void add() {
		String name = rs.getRadioName();
		String url = rs.getRadioUrl();
		if (name!=null && url!=null) {
			Mms mms = new Mms(name, url);
			if (add(mms)) 
				rs.setInfoText("�����ظ�");
		}
	}
	
	private boolean add(Mms mms) {
		boolean repaet = rs.selectedListModel.contains(mms);
		if (!repaet) {
			rs.selectedListModel.addElement(mms);
			rs.getJList().validate();
			rs.clearText();
		}
		return repaet;
	}
}
