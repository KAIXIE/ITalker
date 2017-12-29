package net.qiujuer.italker.push.frags;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.bank.BankItemModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.presenter.bank.BankContract;
import net.qiujuer.italker.factory.presenter.bank.BankPresenter;
import net.qiujuer.italker.push.R;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankcardFragment extends PresenterFragment<BankContract.Presenter> implements BankContract.View {

    private Adapter mAdapter = new Adapter();

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;


    public BankcardFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_bankcard;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadList();
    }

    @Override
    public void listLoaded(List<BankItemModel> model) {
        mAdapter.replace(model);
    }

    @Override
    protected BankContract.Presenter initPresenter() {
        return new BankPresenter(this);
    }

    class Adapter extends RecyclerAdapter<BankItemModel> {

        @Override
        protected int getItemViewType(int position, BankItemModel bankItemModel) {
            return R.layout.cell_bankcard;
        }

        @Override
        protected ViewHolder<BankItemModel> onCreateViewHolder(View root, int viewType) {
            return new BankcardFragment.ViewHolder(root);
        }
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<BankItemModel> {

        @BindView(R.id.cardType)
        TextView mCardType;
        @BindView(R.id.bankName)
        TextView mBankName;
        @BindView(R.id.cardNumber)
        TextView mCardNumber;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BankItemModel bankItemModel) {
            mCardType.setText(bankItemModel.getCardType());
            mBankName.setText(bankItemModel.getBankName());
            mCardNumber.setText(bankItemModel.getCardNumber());
        }
    }

}
