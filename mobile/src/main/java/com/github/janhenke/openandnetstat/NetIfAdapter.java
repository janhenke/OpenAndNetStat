package com.github.janhenke.openandnetstat;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import android.util.Log;
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

		final TextView name;
		final TextView flags;
		final TextView addresses;

		public ViewHolder(@NonNull final View itemView) {
			super(itemView);

			name = itemView.findViewById(R.id.name);
			flags = itemView.findViewById(R.id.flags);
			addresses = itemView.findViewById(R.id.addresses);
		}

		public TextView getName() {
			return name;
		}

		public TextView getFlags() {
			return flags;
		}

		public TextView getAddresses() {
			return addresses;
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

		holder.getName().setText(networkInterface.getDisplayName());

		final List<String> flags = new LinkedList<>();
		try {
			if (networkInterface.isUp()) {
				flags.add("Up");
			}
			if (networkInterface.isLoopback()) {
				flags.add("Loopback");
			}
			if (networkInterface.isPointToPoint()) {
				flags.add("PointToPont");
			}
			if (networkInterface.isVirtual()) {
				flags.add("Virtual");
			}
		} catch (final SocketException e) {
			Log.e(NetIfAdapter.class.getSimpleName(), "Getting properties of network interface failed.", e);
		}
		holder.getFlags().setText(String.join(" ", flags));

		final String addresses = networkInterface.getInterfaceAddresses()
												 .stream()
												 .map(i -> i.getAddress() + "/" + i.getNetworkPrefixLength())
												 .collect(Collectors.joining("\n"));
		holder.getAddresses().setText(addresses);
	}
}
