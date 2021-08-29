package com.github.janhenke.openandnetstat;

import java.net.NetworkInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NetIfAdapter extends ListAdapter<NetworkInterface, NetIfAdapter.ViewHolder> {

	public static class ViewHolder extends RecyclerView.ViewHolder {

		final TextView textView;

		public ViewHolder(@NonNull final View itemView) {
			super(itemView);

			textView = itemView.findViewById(R.id.textView);
		}

		public TextView getTextView() {
			return textView;
		}
	}

	protected NetIfAdapter() {
		super(new DiffUtil.ItemCallback<NetworkInterface>() {
			@Override
			public boolean areItemsTheSame(
				@NonNull final NetworkInterface oldItem,
				@NonNull final NetworkInterface newItem
			) {
				return oldItem == newItem;
			}

			@Override
			public boolean areContentsTheSame(
				@NonNull final NetworkInterface oldItem,
				@NonNull final NetworkInterface newItem
			) {
				return oldItem.equals(newItem);
			}
		});
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

		final View view = layoutInflater.inflate(R.layout.netif_item, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		final NetworkInterface networkInterface = getItem(position);

		holder.getTextView().setText(networkInterface.getDisplayName());
	}
}
