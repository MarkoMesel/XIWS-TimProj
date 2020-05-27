package com.student.scheduleservice.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UnitOfWork")
public class UnitOfWork {
	private BundleRepo bundleRepo;
	private CarPriceListRepo carPriceListRepo;
	private CommentRepo commentRepo;
	private LocationRepo locationRepo;
	private MessageRepo messageRepo;
	private PriceListRepo priceListRepo;
	private PriceRepo priceRepo;
	private PublisherTypeRepo publisherTypeRepo;
	private ReservationRepo reservationRepo;
	private ReservationStateRepo reservationStateRepo;
	private UnavailabilityRepo unavailabilityRepo;
	
	@Autowired
	public UnitOfWork(BundleRepo bundleRepo, CarPriceListRepo carPriceListRepo, CommentRepo commentRepo,
			LocationRepo locationRepo, MessageRepo messageRepo, PriceListRepo priceListRepo, PriceRepo priceRepo,
			PublisherTypeRepo publisherTypeRepo, ReservationRepo reservationRepo,
			ReservationStateRepo reservationStateRepo, UnavailabilityRepo unavailabilityRepo) {
		super();
		this.bundleRepo = bundleRepo;
		this.carPriceListRepo = carPriceListRepo;
		this.commentRepo = commentRepo;
		this.locationRepo = locationRepo;
		this.messageRepo = messageRepo;
		this.priceListRepo = priceListRepo;
		this.priceRepo = priceRepo;
		this.publisherTypeRepo = publisherTypeRepo;
		this.reservationRepo = reservationRepo;
		this.reservationStateRepo = reservationStateRepo;
		this.unavailabilityRepo = unavailabilityRepo;
	}
	public BundleRepo getBundleRepo() {
		return bundleRepo;
	}
	public CarPriceListRepo getCarPriceListRepo() {
		return carPriceListRepo;
	}
	public CommentRepo getCommentRepo() {
		return commentRepo;
	}
	public LocationRepo getLocationRepo() {
		return locationRepo;
	}
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}
	public PriceListRepo getPriceListRepo() {
		return priceListRepo;
	}
	public PriceRepo getPriceRepo() {
		return priceRepo;
	}
	public PublisherTypeRepo getPublisherTypeRepo() {
		return publisherTypeRepo;
	}
	public ReservationRepo getReservationRepo() {
		return reservationRepo;
	}
	public ReservationStateRepo getReservationStateRepo() {
		return reservationStateRepo;
	}
	public UnavailabilityRepo getUnavailabilityRepo() {
		return unavailabilityRepo;
	}
}
